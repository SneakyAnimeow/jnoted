package club.anims.jnoted.services;

import com.lambdaworks.crypto.SCryptUtil;
import org.springframework.stereotype.Service;

@Service
public class HashingService implements IHashingService {
    @Override
    public String hash(String password) {
        return SCryptUtil.scrypt(password, 16384, 8, 1);
    }

    @Override
    public boolean verify(String password, String hash) {
        return SCryptUtil.check(password, hash);
    }
}
