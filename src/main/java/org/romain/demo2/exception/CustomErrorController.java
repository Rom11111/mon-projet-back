package org.romain.demo2.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.romain.demo2.dto.ApiResponseDto;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * Ce contrôleur me permet d’intercepter les erreurs globales (genre 404, 405…).
 * Plutôt que de laisser Spring afficher une page HTML moche,
 * je renvoie une réponse JSON propre avec un message clair.
 */
@Controller
public class CustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    /**
     * Je récupère ErrorAttributes de Spring pour accéder aux infos d'erreur.
     */
    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    /**
     * Cette méthode s'exécute quand une requête tombe sur une erreur (genre route inconnue).
     * Elle est automatiquement appelée via "/error" par Spring.
     */
    @RequestMapping("/error")
    public ResponseEntity<ApiResponseDto<?>> handleError(HttpServletRequest request) {
        // Je convertis la requête classique en WebRequest pour la passer à Spring
        WebRequest webRequest = new ServletWebRequest(request);

        // Je récupère les infos d’erreur (code HTTP, message, etc.)
        Map<String, Object> attributes = errorAttributes.getErrorAttributes(
                webRequest,
                ErrorAttributeOptions.defaults()
        );

        // Je récupère le code HTTP (ex : 404) ou 500 par défaut
        int status = (int) attributes.getOrDefault("status", 500);

        // Je récupère le message d’erreur (ex : "Not Found", "Method Not Allowed"...)
        String message = (String) attributes.getOrDefault("error", "Erreur inconnue");

        // Je construis une réponse JSON avec le message + data null
        return ResponseEntity.status(status)
                .body(new ApiResponseDto<>(message, null));
    }
}
