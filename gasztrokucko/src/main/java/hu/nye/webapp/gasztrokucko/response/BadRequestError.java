package hu.nye.webapp.gasztrokucko.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class BadRequestError {

    private List<String> messages;

}
