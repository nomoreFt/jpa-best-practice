package jpa.practice.relationship.onetomany2.service;

import jpa.practice.relationship.onetomany2.entity.Image;
import jpa.practice.relationship.onetomany2.entity.Comment;
import jpa.practice.relationship.onetomany2.entity.Like;
import jpa.practice.relationship.onetomany2.entity.Post;
import jpa.practice.relationship.onetomany2.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {
    private final PostRepository postRepository;

    public BoardService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public void init(){
        Post p1 = new Post("JPA","des1");
        Comment c1 = new Comment("comment1");
        Comment c2 = new Comment("comment2");
        Image image1 = new Image("Infra");
        Image image2 = new Image("Dev");
        Like like1 = new Like(1);
        p1.addComment(c1);
        p1.addComment(c2);
        p1.addImage(image1);
        p1.addImage(image2);
        p1.addLike(like1);

        Post p2 = new Post("JPA2","des2");
        Comment c3 = new Comment("comment3");
        Comment c4 = new Comment("comment4");
        Image image3 = new Image("Infra2");
        Image image4 = new Image("Dev2");
        p2.addComment(c3);
        p2.addComment(c4);
        p2.addImage(image3);
        p2.addImage(image4);

        Post p3 = new Post("JPA3","des3");
        Image image5 = new Image("Infra3");
        Image image6 = new Image("Dev3");
        p3.addImage(image5);
        p3.addImage(image6);

        Post p4 = new Post("JPA4","des4");
        Comment c5 = new Comment("comment5");
        Image image7 = new Image("Infra4");
        p4.addComment(c5);
        p4.addImage(image7);

        Post p5 = new Post("JPA5","des5");
        Comment c6 = new Comment("comment6");
        p5.addComment(c6);

        postRepository.saveAllAndFlush(List.of(p1,p2,p3,p4,p5));
    }

    @Transactional(readOnly = true)
    public void fetchPostWithAllThrowException(){
        /**
         * MultipleBagFetchException 발생
         */
        Post jpa = postRepository.fetchByTitleWithCommentsAndImagesThrowException("JPA");
        System.out.println(jpa);
    }

    /**
     * 단일은 어차피 사용시에 Select 쿼리가 나가기 때문에 최초 1회만 가져와도 상관없긴 하다.
     */
    @Transactional(readOnly = true)
    public void fetchPostWithAllSeparate(){

        Post post = postRepository.fetchByTitleWithImages("JPA");
        post = postRepository.fetchByTitleWithComments("JPA");
        post = postRepository.fetchByTitleWithLikes("JPA");
        System.out.println(post.getImages());
        System.out.println(post.getComments());
        System.out.println(post.getLikes());


        /**
         * [Hibernate]
         *     select
         *         p1_0.id,
         *         p1_0.description,
         *         i1_0.post_id,
         *         i1_0.id,
         *         i1_0.url,
         *         p1_0.title
         *     from
         *         post p1_0
         *     join
         *         image i1_0
         *             on p1_0.id=i1_0.post_id
         *     where
         *         p1_0.title=?
         *  binding parameter (1:VARCHAR) <- [JPA]
         *
         * [Hibernate]
         *     select
         *         p1_0.id,
         *         c1_0.post_id,
         *         c1_0.id,
         *         c1_0.contents,
         *         p1_0.description,
         *         p1_0.title
         *     from
         *         post p1_0
         *     join
         *         comment c1_0
         *             on p1_0.id=c1_0.post_id
         *     where
         *         p1_0.title=?
         * binding parameter (1:VARCHAR) <- [JPA]
         *
         * [Hibernate]
         *     select
         *         p1_0.id,
         *         p1_0.description,
         *         l1_0.post_id,
         *         l1_0.id,
         *         l1_0.count,
         *         p1_0.title
         *     from
         *         post p1_0
         *     join
         *         likes l1_0
         *             on p1_0.id=l1_0.post_id
         *     where
         *         p1_0.title=?
         * binding parameter (1:VARCHAR) <- [JPA]
         *
         * [Image{id=1, url='Infra'}, Image{id=2, url='Dev'}]
         * [Comment{id=1, contents='comment1'}, Comment{id=2, contents='comment2'}]
         * [Like{id=1, count=1}]
         */
//
    }

    /**
     * x - to Many를 모두 가져오기 위해 각각 LEFT FETCH JOIN으로 하여 Post 기준으로 누락없이 가져온다.
     * MultipleBagFetchException 발생하지 않음
     */
    @Transactional(readOnly = true)
    public void fetchPostAllWithAll(){
        List<Post> posts = postRepository.fetchAllWithComments();
        posts = !posts.isEmpty() ? postRepository.fetchAllWithImages() : posts;
        posts = !posts.isEmpty() ? postRepository.fetchAllWithLikes() : posts;

        System.out.println("fetchPostAllWithAll: " + posts);

        /**
         * [Hibernate]
         *     select
         *         p1_0.id,
         *         c1_0.post_id,
         *         c1_0.id,
         *         c1_0.contents,
         *         p1_0.description,
         *         p1_0.title
         *     from
         *         post p1_0
         *     left join
         *         comment c1_0
         *             on p1_0.id=c1_0.post_id
         *
         * [Hibernate]
         *     select
         *         p1_0.id,
         *         p1_0.description,
         *         i1_0.post_id,
         *         i1_0.id,
         *         i1_0.url,
         *         p1_0.title
         *     from
         *         post p1_0
         *     left join
         *         image i1_0
         *             on p1_0.id=i1_0.post_id
         *
         * [Hibernate]
         *     select
         *         p1_0.id,
         *         p1_0.description,
         *         l1_0.post_id,
         *         l1_0.id,
         *         l1_0.count,
         *         p1_0.title
         *     from
         *         post p1_0
         *     left join
         *         likes l1_0
         *             on p1_0.id=l1_0.post_id
         *
         * fetchPostAllWithAll: [Post{id=1, title='JPA', description='des1', comments=[Comment{id=1, contents='comment1'}, Comment{id=2, contents='comment2'}], images=[Image{id=1, url='Infra'}, Image{id=2, url='Dev'}]}, Post{id=2, title='JPA2', description='des2', comments=[Comment{id=3, contents='comment3'}, Comment{id=4, contents='comment4'}], images=[Image{id=3, url='Infra2'}, Image{id=4, url='Dev2'}]}, Post{id=3, title='JPA3', description='des3', comments=[], images=[Image{id=5, url='Infra3'}, Image{id=6, url='Dev3'}]}, Post{id=4, title='JPA4', description='des4', comments=[Comment{id=5, contents='comment5'}], images=[Image{id=7, url='Infra4'}]}, Post{id=5, title='JPA5', description='des5', comments=[Comment{id=6, contents='comment6'}], images=[]}]
         */
    }






}
