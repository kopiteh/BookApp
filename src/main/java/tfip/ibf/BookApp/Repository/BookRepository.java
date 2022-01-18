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

    public void save(String key, String value) {
        template.opsForValue().set(key, value, 10L, TimeUnit.MINUTES);
    }

    public Optional<String> get(String key) {
        String value = template.opsForValue().get(key);
        return Optional.ofNullable(value);
    }


}
