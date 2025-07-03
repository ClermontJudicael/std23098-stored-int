package com.example.demo.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@RestController
public class StoredIntController {

    private static final String FILE_PATH = "/tmp/stored-int.txt"; // Chemin d'accès au fichier dans l'environnement Lambda

    @GetMapping("/stored-int")
    public String storedInt() {
        Path path = Paths.get(FILE_PATH);
        Random random = new Random();
        int numberToReturn;

        try {
            if (Files.exists(path)) {
                // a) Le fichier existe, lisez le nombre
                String content = Files.readString(path).trim();
                try {
                    numberToReturn = Integer.parseInt(content);
                    System.out.println("Fichier trouvé. Nombre lu : " + numberToReturn);
                    return "Nombre lu du fichier : " + numberToReturn;
                } catch (NumberFormatException e) {
                    // Le contenu n'est pas un nombre valide, on le traite comme non existant
                    System.err.println("Le contenu du fichier n'est pas un nombre valide. Création d'un nouveau fichier. Erreur: " + e.getMessage());
                    numberToReturn = random.nextInt(1000); // Génère un nombre aléatoire entre 0 et 999
                    Files.writeString(path, String.valueOf(numberToReturn));
                    System.out.println("Fichier corrompu remplacé par : " + numberToReturn);
                    return "Fichier existant mais corrompu. Nouveau nombre aléatoire généré : " + numberToReturn;
                }
            } else {
                // b) Le fichier n'existe pas, créez-le et écrivez un nombre aléatoire
                numberToReturn = random.nextInt(1000); // Génère un nombre aléatoire entre 0 et 999
                Files.writeString(path, String.valueOf(numberToReturn));
                System.out.println("Fichier non trouvé. Créé avec le nombre aléatoire : " + numberToReturn);
                return "Fichier créé avec le nombre aléatoire : " + numberToReturn;
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'accès au fichier " + FILE_PATH + " : " + e.getMessage());
            // En cas d'erreur d'E/S, retournez un message d'erreur
            return "Erreur lors de l'accès au fichier : " + e.getMessage();
        }
    }
}