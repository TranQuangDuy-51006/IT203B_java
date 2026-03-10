package SS3.B6;

import java.util.List;

record Post(String title, List<String> tags) {}

public class Main {

    public static void main(String[] args) {

        List<Post> posts = List.of(
                new Post("Java Post", List.of("java", "backend")),
                new Post("Python Post", List.of("python", "data"))
        );

        List<String> allTags = posts.stream()
                .flatMap(post -> post.tags().stream())
                .toList();

        System.out.println(allTags);
    }
}