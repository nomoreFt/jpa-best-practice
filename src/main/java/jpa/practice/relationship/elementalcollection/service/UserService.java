package jpa.practice.relationship.elementalcollection.service;

import jpa.practice.relationship.elementalcollection.dto.UserDto;
import jpa.practice.relationship.elementalcollection.entity.User;
import jpa.practice.relationship.elementalcollection.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void init() {
        // Add user
        User user = new User("Alicia Tom");
        user.addFavoriteBook("title1");
        user.addFavoriteBook("title2");
        user.addFavoriteBook("title3");

        User user2 = new User("Micheal Smith");
        user2.addFavoriteBook("title4");
        user2.addFavoriteBook("title5");
        user2.addFavoriteBook("title6");

        userRepository.saveAllAndFlush(List.of(user, user2));
    }

    @Transactional(readOnly = true)
    public void fetchUserWithDtoV1() {
        List<UserDto> aliciaTom = userRepository.findUserDtoByName("Alicia Tom");

        aliciaTom.stream()
                        .map(UserDto::favoriteBook)
                        .forEach(System.out::println);

        /**
         * [Hibernate]
         *     select
         *         u1_0.id,
         *         u1_0.name,
         *         fb1_0.book_title
         *     from
         *         users u1_0
         *     join
         *         user_favorite_books fb1_0
         *             on u1_0.id=fb1_0.user_id
         *     where
         *         u1_0.name=?
         * binding parameter (1:VARCHAR) <- [Alicia Tom]
         * title1
         * title2
         * title3
         */
    }

    /**
     * delete 하나를 해도 user가 가진 3개의 즐겨찾기 책이 모두 삭제되고 다시 2개의 즐겨찾기 책이 추가된다.
     * 쿼리가 많아..
     */
    @Transactional
    public void removeFavoriteBook() {
        User user = userRepository.findById(1L).orElseThrow();
        user.removeFavoriteBook("title1");

        /**
         * [Hibernate]
         *     select
         *         u1_0.id,
         *         u1_0.name
         *     from
         *         users u1_0
         *     where
         *         u1_0.id=?
         * binding parameter (1:BIGINT) <- [1]
         *
         * [Hibernate]
         *     select
         *         fb1_0.user_id,
         *         fb1_0.book_title
         *     from
         *         user_favorite_books fb1_0
         *     where
         *         fb1_0.user_id=?
         * binding parameter (1:BIGINT) <- [1]
         *
         * Initiating transaction commit
         * Committing JPA transaction on EntityManager [SessionImpl(1700257002<open>)]
         * committing
         *
         * [Hibernate]
         *     delete
         *     from
         *         user_favorite_books
         *     where
         *         user_id=?
         * binding parameter (1:BIGINT) <- [1]
         *
         * [Hibernate]
         *     insert
         *     into
         *         user_favorite_books
         *         (user_id, book_title)
         *     values
         *         (?, ?)
         * binding parameter (1:BIGINT) <- [1]
         * binding parameter (2:VARCHAR) <- [title2]
         *
         * [Hibernate]
         *     insert
         *     into
         *         user_favorite_books
         *         (user_id, book_title)
         *     values
         *         (?, ?)
         *
         * binding parameter (1:BIGINT) <- [1]
         * binding parameter (2:VARCHAR) <- [title3]
         */
    }

}
