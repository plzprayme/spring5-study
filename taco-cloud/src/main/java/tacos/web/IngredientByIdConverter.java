package tacos.web;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import tacos.Ingredient;
import tacos.data.IngredientRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class IngredientByIdConverter implements Converter<String, Ingredient> {
    private IngredientRepository ingredientRepository;

    @Override
    public Ingredient convert(String id) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
        return optionalIngredient.orElse(null);
//        return optionalIngredient.isPresent() ? optionalIngredient.get() : null;
    }
}
