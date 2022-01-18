package tfip.ibf.BookApp.Repository;
import static tfip.ibf.BookApp.Constants.*;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {
    
    @Autowired
    @Qualifier(THE_BEAN)
    private RedisTemplate<String, String> template;

    public void save(String works_id, String value) {
        template.opsForValue().set(works_id, value, 10L, TimeUnit.MINUTES);
    }

    public Optional<String> get(String works_id) {
        String value = template.opsForValue().get(works_id);
        return Optional.ofNullable(value);
    }


}
