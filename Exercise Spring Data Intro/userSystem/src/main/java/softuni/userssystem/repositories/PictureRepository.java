package softuni.userssystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.userssystem.models.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
}
