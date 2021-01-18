package tacos.data;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tacos.Ingredient;
import tacos.Taco;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
@RequiredArgsConstructor
public class JdbcTacoRepository implements TacoRepository {

    private final JdbcTemplate jdbc;


    @Override
    public Taco save(Taco taco) {
        // 타코를 저장하기 위해서는 Ingredient의 id를 포함한다 ( 외래키)
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);

        for (Ingredient ingredient : taco.getIngredients()) {
            saveIngredientToTaco(ingredient, tacoId);

        }

        return taco;
    }

    private long saveTacoInfo(Taco taco) {
        // 타코 생성 시간, 이름 저장
        taco.setCreatedAt(new Date());
        // Factory에서 각 타입들을 지정해준 후
        // Creator에서 값을 할당해준다.
        PreparedStatementCreator psc = new PreparedStatementCreatorFactory(
                "insert into Taco (name, createdAt) values (?, ?)",
                Types.VARCHAR, Types.TIMESTAMP
        ).newPreparedStatementCreator(
                Arrays.asList(
                        taco.getName(),
                        new Timestamp(taco.getCreatedAt().getTime())));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);
        // update(PreparedStatementCreator, Keyholder)
        // Keyholder에 방금 insert한 녀석의 id가 담겨진다.

        return keyHolder.getKey().longValue();
    }


    // 조인 테이블
    private void saveIngredientToTaco(
            Ingredient ingredient, long tacoId) {
        jdbc.update("insert into Taco_ingredients (taco, ingredient) "
                        + "values (?, ?)", tacoId, ingredient.getId());
    }
}
