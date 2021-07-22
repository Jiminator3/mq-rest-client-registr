package ru.jiminator.mqrestclientregistr

//import org.apache.logging.log4j.kotlin.logger
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.notFound
import org.springframework.web.servlet.function.ServerResponse.ok
import org.springframework.web.servlet.function.ServerResponse.status
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_OK
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


//class RestApiHandler(
//    private val repository: ClientRepository,
//    private val documentDownloadUrl: URI,
//) {
//
//    fun Clients(id: String): ServerResponse {
//        val documentExtId: List<Client> = repository.findAll().toList() ?: return notFound().build()
//        val request = HttpRequest.newBuilder()
//            .uri(documentDownloadUrl.resolve(documentExtId))
//            .build()
//        val resp = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream())
//        return when (resp.statusCode()) {
//            HTTP_OK -> {
//                val contentLen = resp.headers().firstValue("Content-Length").map { it.toLong() }.orElse(null)
//                val mediaType = resp.headers().firstValue("Content-Type")
//                    .map { MediaType.parseMediaType(it) }
//                    .orElse(MediaType.APPLICATION_OCTET_STREAM)
//                val stream = resp.body()
//                ok().apply {
//                    contentType(mediaType)
//                    if (contentLen != null) {
//                        contentLength(contentLen)
//                    }
//                }.body(InputStreamResource(stream))
//            }
//            HTTP_NOT_FOUND -> {
//                notFound().build()
//            }
//            else -> {
//                log.warn { "Unprocessable response status $resp for $documentId." }
//                status(HttpStatus.INTERNAL_SERVER_ERROR).build()
//            }
//        }
//    }
//}

//fun Clients(id: String): ServerResponse {
//        return clients.findAll().toList()
//    }
//
//}
