package ru.jiminator.mqrestclientregistr

import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@AutoConfigureEmbeddedDatabase(type = AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES)
@SpringBootTest(classes = [MqRestClientRegistrApplication::class])
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
class WebMvcClientResourcesTest(
    @Autowired val mockMvc: MockMvc
) {
    companion object {
        @JvmStatic
        @BeforeAll
        fun databasePopulation(@Autowired clientRepository: ClientRepository) {
            val client = Client(factAddress = "5mkr", regAddress = "6mkr", phone = 89889778864)
            clientRepository.save(client)
        }
    }

    @Test
    fun `01 - Test index route of client resource`() {
        mockMvc.get("/client").andExpect { status { isOk() } }
            .andExpect {
                content {
                    json("[{\"id\":1,\"factAddress\":\"5mkr\",\"regAddress\":\"6mkr\",\"phone\":89889778864}]")
                }
            }
    }

    @Test
    fun `02 - Test find route of client resource by id`() {
        mockMvc.get("/client/1").andExpect { status { isOk() } }
            .andExpect {
                content {
                    json("{\"id\":1,\"factAddress\":\"5mkr\",\"regAddress\":\"6mkr\",\"phone\":89889778864}")
                }
            }
    }
}