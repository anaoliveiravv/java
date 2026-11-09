package service;

import org.springframework.stereotype.Service;

@Service //diz ao Spring que essa parte contém a lógica da aplicação, que é o Service.
public class MovieService {

    public String checkRating(double rating) {
        if (rating < 0 || rating > 10) {
            return "Invalid rating";
        }
        return "Valid rating";
    }
}
