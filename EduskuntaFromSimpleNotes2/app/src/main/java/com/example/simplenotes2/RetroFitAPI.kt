import retrofit2.Retrofit
import retrofit2.Converter
import kotlinx.serialization.json.Json
import retrofit2.converter.gson.GsonConverterFactory


object RetroFitAPI {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(RetroFitAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}