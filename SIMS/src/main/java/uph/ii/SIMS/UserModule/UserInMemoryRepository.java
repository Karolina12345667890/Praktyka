package uph.ii.SIMS.UserModule;

import java.util.HashMap;
import java.util.Optional;

class UserInMemoryRepository implements UserRepository {
    
    private HashMap<Long, User> map = new HashMap<>();
    
    @Override
    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(map.get(id));
    }
    
    @Override
    public void save(User user) {
        map.put(user.dto().getId(), user);
    }
}
