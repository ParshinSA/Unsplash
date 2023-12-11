package com.example.unsplash._common.helpers.loading

import android.content.Context
import com.bumptech.glide.Glide
import java.io.File

class HelperWithCachingFileImpl(
    private val context: Context
) : HelperWithCachingFile {

    private val downloadDirectory = context.filesDir

    override fun loadFile(url: String, nameFile: String): String? {
	  val pathToFile = downloadDirectory.absolutePath + '/' + nameFile
	  return if (isFileDownloaded(pathToFile)) pathToFile
	  else download(nameFile, url)
    }

    override fun deleteFile(nameFile: String) {
	  val pathToFile = downloadDirectory.absolutePath + '/' + nameFile
	  if (isFileDownloaded(pathToFile))
		File(downloadDirectory, nameFile).delete()
    }

    private fun download(nameFile: String, url: String): String? {
	  val file = File(downloadDirectory, nameFile)

	  return try {
		val glideFile = Glide.with(context)
		    .asFile()
		    .load(url)
		    .submit()
		    .get()

		glideFile.copyTo(file)
		file.absolutePath
	  } catch (t: Throwable) {
		file.delete()
		null
	  }
    }

    private fun isFileDownloaded(pathToFile: String) = File(pathToFile).exists()
}