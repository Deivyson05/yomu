package br.com.yomu.gamificacaoDaLeitura.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration  // Diz ao Spring que esta é uma classe de configuração
public class SwaggerConfig {

    @Bean  // Expõe um objeto OpenAPI no contexto da aplicação
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Yomu - API de Gamificação de Leitura")
                        .description("API REST para gerenciamento de leitura gamificada com sistema de XP, rankings, metas e interações sociais")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipe DokeyCode")
                                .url("https://yomu.com.br"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de Desenvolvimento"),
                        new Server()
                                .url("https://yomu-api-0tys.onrender.com/yomu")
                                .description("Servidor de Produção")
                ));
    }
}
