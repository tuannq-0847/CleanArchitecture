package com.krause.data.repository

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.krause.data.database.dao.MemeDao
import com.krause.data.database.model.convertToMemes
import com.krause.data.networking.MemeApi
import com.krause.data.networking.getData
import com.krause.data.utils.Connectivity
import com.krause.domain.model.Meme
import com.krause.domain.repository.MemeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject

class MemeRepositoryImpl @Inject constructor(
    private val memeApi: MemeApi,
    private val memeDao: MemeDao,
    connectivity: Connectivity,
    private val context: Context
) : BaseRepository(connectivity), MemeRepository {
    override suspend fun getMemes(): Result<List<Meme>> {
        return fetchDataWithCached(
            dataProvider = {
                memeApi.getMemes().getData(
                    cacheAction = {
                        memeDao.cacheMemes(it.convertToMemeEntities())
                    },
                    fetchFromCacheAction = {
                        memeDao.getCachedMemes().convertToMemes()
                    }
                )
            },
            cacheProvider = {
                memeDao.getCachedMemes().convertToMemes()
            }
        )
    }

    override suspend fun saveImage(bitmap: Bitmap): Result<Boolean> {
        withContext(Dispatchers.IO) {
            //Generating a file name
            val filename = "${System.currentTimeMillis()}.jpg"

            //Output stream
            var fos: OutputStream? = null

            //For devices running android >= Q
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                //getting the contentResolver
                context.contentResolver?.also { resolver ->

                    //Content resolver will process the contentvalues
                    val contentValues = ContentValues().apply {

                        //putting file information in content values
                        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                    }

                    //Inserting the contentValues to contentResolver and getting the Uri
                    val imageUri: Uri? =
                        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                    //Opening an outputstream with the Uri that we got
                    fos = imageUri?.let { resolver.openOutputStream(it) }
                }
            } else {
                //These for devices running on android < Q
                //So I don't think an explanation is needed here
                val imagesDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val image = File(imagesDir, filename)
                fos = FileOutputStream(image)
            }

            fos?.use {
                //Finally writing the bitmap to the output stream that we opened
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            }
        }
        return Result.success(true)
    }
}
