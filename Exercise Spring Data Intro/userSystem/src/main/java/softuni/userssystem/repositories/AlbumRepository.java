package softuni.userssystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.userssystem.models.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
}
