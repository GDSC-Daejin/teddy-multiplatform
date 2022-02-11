package data.remote

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import kotlinx.serialization.json.Json

internal val API_URL : String = TODO("Enter your API URL here")

@Suppress("FunctionName")
fun <T : HttpClientEngineConfig> TeddyApiHttpClient(
    engineFactory: HttpClientEngineFactory<T>,
    block: HttpClientConfig<T>.() -> Unit = {}
) = HttpClient(engineFactory) {

    developmentMode = true
    expectSuccess = false

    install(HttpTimeout) {
        requestTimeoutMillis = 5000L
        connectTimeoutMillis = 5000L
    }

    install(ContentNegotiation) {
        register(
            contentType = ContentType.Application.Json,
            converter = KotlinxSerializationConverter(
                Json {
                    ignoreUnknownKeys = true
                    allowSpecialFloatingPointValues = true
                    useArrayPolymorphism = true
                    prettyPrint = true
                    allowStructuredMapKeys = true
                    coerceInputValues = true
                    useAlternativeNames = false
                }
            )
        )
    }
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                println(message)
            }
        }
        level = LogLevel.ALL
    }

    defaultRequest {

        contentType(ContentType.Application.Json)

        headers {
            accept(ContentType.Application.Json)
        }

        url {
            protocol = URLProtocol.HTTPS
            host = API_URL
        }
    }

    block()
}