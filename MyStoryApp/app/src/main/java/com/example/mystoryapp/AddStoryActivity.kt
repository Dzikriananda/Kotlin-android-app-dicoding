package com.example.mystoryapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mystoryapp.databinding.ActivityAddStoryBinding
import com.example.mystoryapp.utility.*
import com.example.mystoryapp.utility.Preferences
import com.example.mystoryapp.viewmodels.AddStoryViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AddStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStoryBinding
    private var getFile: File? = null
    private lateinit var currentPhotoPath: String
    private  lateinit var foto: File
    private lateinit var viewModel: AddStoryViewModel
    private lateinit var preferences: Preferences
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        preferences = Preferences(this)
        token = preferences.Get_Token()!!

        val viewModelFactory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[AddStoryViewModel::class.java]

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this@AddStoryActivity,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.galleryButton.setOnClickListener{
            startGallery()
        }

        binding.CameraButton.setOnClickListener{
            startCameraX()
        }

        binding.buttonAdd.setOnClickListener{
            uploadImage()
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@AddStoryActivity)
                getFile = myFile
                binding.previewImageView.setImageURI(uri)
            }
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)

    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.data?.getSerializableExtra("picture")
            } as? File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            myFile?.let { file ->
                rotateFile(file, isBackCamera)
                getFile = file
                binding.previewImageView.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    private fun uploadImage() {
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)
            val desc = binding.edAddDescription.text.toString()
            val lat = binding.edAddLatitude.text.toString()
            val lon = binding.edAddLongitude.text.toString()
            val description = desc.toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            val latitude = lat.toRequestBody("text/plain".toMediaType())
            val longitude = lon.toRequestBody("text/plain".toMediaType())

            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )

            Log.i("value",lat)

            if((lat == "") && (lon == "")){
                UploadwithoutLatLon(description,imageMultipart)
            }
            else if(lat == "" || lon == ""){
                Toast.makeText(this,"Fill both Lat and Lon or Nothing!",Toast.LENGTH_SHORT).show()
            }
            else{
                UploadwithLatLon(description,imageMultipart, latitude , longitude )
            }
        }
    }



    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    fun UploadwithoutLatLon(description: RequestBody, imageMultipart: MultipartBody.Part){
        viewModel.addStory(token,description,imageMultipart).observe(this){
            when(it){
                is Result.Success -> {
                    Toast.makeText(this@AddStoryActivity, "sukses", Toast.LENGTH_SHORT).show()
                    finish()
                }
                is Result.Error -> {
                    Toast.makeText(this@AddStoryActivity, it.error, Toast.LENGTH_LONG).show()
                    Log.i("ERROR",it.error)
                }
                else -> {}
            }
        }
    }

    fun UploadwithLatLon(description: RequestBody, imageMultipart: MultipartBody.Part,Lattitude: RequestBody, Longitude: RequestBody){
        viewModel.addStoryWithLatLon(token,description,imageMultipart,Lattitude,Longitude).observe(this){
            when(it){
                is Result.Success -> {
                    Toast.makeText(this@AddStoryActivity, "sukses", Toast.LENGTH_SHORT).show()
                    finish()
                }
                is Result.Error -> {
                    Toast.makeText(this@AddStoryActivity, it.error, Toast.LENGTH_LONG).show()
                    Log.i("ERROR",it.error)
                }
                else -> {}
            }
        }
    }
}