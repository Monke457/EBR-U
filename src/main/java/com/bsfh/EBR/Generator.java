package com.bsfh.EBR;

import com.bsfh.EBR.model.*;
import com.bsfh.EBR.service.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.rowset.serial.SerialBlob;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class Generator {
    @Bean
    public CommandLineRunner loadData(PasswordEncoder encoder,
                                      DBService<Author> authorService,
                                      DBService<Genre> genreService,
                                      DBService<Book> bookService,
                                      DBService<Customer> customerService,
                                      DBService<Subscription> subService,
                                      DBService<BlobFile> blobService) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());

            if (authorService.exists(Author.class)) {
                logger.info("Using existing database");
                return;
            }

            logger.info("...generating author entity...");
            //String firstName, String lastName, Set<Book> books
            Author king = new Author("Stephen", "King", new HashSet<>());
            Author pullman = new Author("Philip", "Pullman", new HashSet<>());
            Author rowling = new Author("Joanne K.", "Rowling", new HashSet<>());
            Author tolkien = new Author("John R. R.", "Tolkien", new HashSet<>());
            Author martin = new Author("George R. R.", "Martin", new HashSet<>());
            Author fitz = new Author("F. Scott", "Fitzgerald", new HashSet<>());
            Author kafka = new Author("Franz", "Kafka", new HashSet<>());
            Author hemming = new Author("Ernest", "Hemingway", new HashSet<>());
            Author herbert = new Author("Frank", "Herbert", new HashSet<>());
            Author wilde = new Author(" Oscar", "Wilde", new HashSet<>());
            Author orwell = new Author("George", "Orwell", new HashSet<>());
            authorService.createAll(new Author[]{king, pullman, rowling, tolkien, martin, fitz, kafka,
                    hemming, herbert, wilde, orwell});

            logger.info("...generating genre entity...");
            Genre horror = new Genre("Horror", new HashSet<>());
            Genre drama = new Genre("Drama", new HashSet<>());
            Genre thriller = new Genre("Thriller", new HashSet<>());
            Genre romance = new Genre("Romance", new HashSet<>());
            Genre comedy = new Genre("Comedy", new HashSet<>());
            Genre nonFiction = new Genre("Non Fiction", new HashSet<>());
            Genre biography = new Genre("Biography", new HashSet<>());
            Genre fantasy = new Genre("Fantasy", new HashSet<>());
            Genre sciFi = new Genre("Science Fiction", new HashSet<>());
            Genre business = new Genre("Business", new HashSet<>());
            Genre classics = new Genre("Classics", new HashSet<>());
            Genre mystery = new Genre("Mystery", new HashSet<>());
            Genre crime = new Genre("Crime", new HashSet<>());
            Genre history = new Genre("History", new HashSet<>());
            Genre youngAdult = new Genre("Young Adult", new HashSet<>());
            Genre adv = new Genre("Adventure", new HashSet<>());
            Genre pol = new Genre("Politics", new HashSet<>());
            genreService.createAll(new Genre[]{horror, drama, thriller, romance, comedy, nonFiction, biography, biography,
                    fantasy, sciFi, business, classics, mystery, crime, history, youngAdult, adv, pol});

            logger.info("...generating cover entity...");
            BlobFile cover1984 = new BlobFile("1984", "image/jpg", new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/img/1984.jpg")).readAllBytes()), null);
            BlobFile itCover = new BlobFile("it", "image/jpg", new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/img/it.jpg")).readAllBytes()), null);
            BlobFile anFarmCover = new BlobFile("animal-farm", "image/jpg", new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/img/animal-farm.jpg")).readAllBytes()), null);
            BlobFile codCover = new BlobFile("children-of-dune", "image/jpg", new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/img/cod.jpg")).readAllBytes()), null);
            BlobFile cosCover = new BlobFile("chamber-of-secrets", "image/jpg", new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/img/cos.jpg")).readAllBytes()), null);
            BlobFile dorianCover = new BlobFile("dorian-grey", "image/jpg", new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/img/dorian.jpg")).readAllBytes()), null);
            BlobFile duneCover = new BlobFile("dune", "image/jpg", new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/img/dune.jpg")).readAllBytes()), null);
            BlobFile messiahCover = new BlobFile("dune-messiah", "image/jpg", new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/img/dunemess.jpg")).readAllBytes()), null);
            BlobFile fotrCover = new BlobFile("fellowship-of-the-ring", "image/png", new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/img/fotr.png")).readAllBytes()), null);
            BlobFile ggCover = new BlobFile("great-gatsby", "image/jpg", new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/img/gg.jpg")).readAllBytes()), null);
            BlobFile gotCover = new BlobFile("game-of-thrones", "image/jpg", new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/img/got.jpg")).readAllBytes()), null);
            BlobFile metaCover = new BlobFile("metamorphosis", "image/jpg", new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/img/meta.jpg")).readAllBytes()), null);
            BlobFile northCover = new BlobFile("northern-lights", "image/jpg", new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/img/northern-lights.jpg")).readAllBytes()), null);
            BlobFile omatsCover = new BlobFile("old-man-and-the-sea", "image/jpg", new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/img/omats.jpg")).readAllBytes()), null);
            BlobFile psCover = new BlobFile("philosophers-stone", "image/jpg", new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/img/ps.jpg")).readAllBytes()), null);
            BlobFile ttCover = new BlobFile("two-towers", "image/jpeg", new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/img/tt.jpeg")).readAllBytes()), null);

            blobService.createAll(new BlobFile[]{cover1984, itCover, anFarmCover, codCover,
                    cosCover, dorianCover, duneCover, messiahCover, fotrCover, ggCover,
                    gotCover, metaCover, northCover, omatsCover, psCover, ttCover});

            logger.info("...generating book entity...");
            //String title, LocalDate publishDate, String synopsis, int pageCount, float pricePerDay, String isbn, BlobFile cover, Author author, Set<Genre> genres
            Book it = new Book("It",
                    LocalDate.of(1982, 9,15),
                    "Seven children are terrorized by an evil entity that exploits the fears of its victims.",
                    1138,
                    0.05f,
                    "0-670-81302-8",
                    3.2f,
                    itCover,
                    king,
                    Set.of(horror, thriller),
                    new HashSet<>(),
                    new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/book/sample1.epub")).readAllBytes()));

            Book harryPotter1 = new Book("Harry Potter and the Philosopher's Stone",
                    LocalDate.of(1997, 6,26),
                    "A young wizard discovers his magical heritage on his eleventh birthday.",
                    223,
                    0.1f,
                    "0-7475-3269-9",
                    4.5f,
                    psCover,
                    rowling,
                    Set.of(fantasy, youngAdult),
                    new HashSet<>(),
                    new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/book/sample1.epub")).readAllBytes()));

            Book harryPotter2 = new Book("Harry Potter and the Chamber of Secrets",
                    LocalDate.of(1998, 7,2),
                    "Harry and his friends Ron and Hermione investigate attacks and threats at school",
                    251,
                    0.1f,
                    "0-7475-3849-2",
                    4.3f,
                    cosCover,
                    rowling,
                    Set.of(fantasy, youngAdult),
                    new HashSet<>(),
                    new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/book/sample1.epub")).readAllBytes()));

            Book northernLights = new Book("Northern Lights",
                    LocalDate.of(1995, 7,9),
                    "The journey of Lyra Belacqua to the Arctic to search for her missing friend.",
                    399,
                    0.1f,
                    "0-590-54178-1",
                    3.8f,
                    northCover,
                    pullman,
                    Set.of(fantasy, youngAdult),
                    new HashSet<>(),
                    new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/book/sample1.epub")).readAllBytes()));

            Book lotr1 = new Book("The Fellowship of the Ring",
                    LocalDate.of(1954, 7,29),
                    "The first part of Tolkien’s epic masterpiece.",
                    423,
                    0.05f,
                    "978-0007522903",
                    4.9f,
                    fotrCover,
                    tolkien,
                    Set.of(fantasy),
                    new HashSet<>(),
                    new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/book/sample1.epub")).readAllBytes()));

            Book lotr2 = new Book("The Two Towers",
                    LocalDate.of(1954, 11,11),
                    "Frodo and his Companions quest to prevent the Ruling Ring from falling into the hands of the Dark Lord.",
                    352,
                    0.05f,
                    "978-0547928203",
                    4.8f,
                    ttCover,
                    tolkien,
                    Set.of(fantasy),
                    new HashSet<>(),
                    new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/book/sample1.epub")).readAllBytes()));

            Book got = new Book("A Game of Thrones",
                    LocalDate.of(1996, 8,1),
                    "The first novel in A Song of Ice and Fire.",
                    694,
                    0.05f,
                    "0-553-10354-7",
                    4f,
                    gotCover,
                    martin,
                    Set.of(fantasy),
                    new HashSet<>(),
                    new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/book/sample1.epub")).readAllBytes()));

            Book gatsby = new Book("The Great Gatsby",
                    LocalDate.of(1925, 4, 10),
                    "The story of the fabulously wealthy Jay Gatsby and his love for the beautiful Daisy Buchanan, of lavish parties on Long Island.",
                    180,
                    0.05f,
                    "0743273567",
                    4f,
                    ggCover,
                    fitz,
                    Set.of(classics, romance),
                    new HashSet<>(),
                    new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/book/sample1.epub")).readAllBytes()));

            Book meta = new Book("The Metamorphosis",
                    LocalDate.of(1915, 1, 1),
                    "The story of a young man who, transformed overnight into a giant beetle-like insect, becomes an object of disgrace to his family, " +
                            "an outsider in his own home, a quintessentially alienated man. ",
                    201,
                    0.05f,
                    "0553213695 ",
                    3.8f,
                    metaCover,
                    kafka,
                    Set.of(classics, fantasy),
                    new HashSet<>(),
                    new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/book/sample1.epub")).readAllBytes()));

            Book oldMan = new Book("The Old Man and the Sea",
                    LocalDate.of(1952, 9, 1),
                    "The superbly told, tragic story of a Cuban fisherman in the Gulf Stream and the giant Marlin he kills and loses.",
                    96,
                    0.05f,
                    "0684801221",
                    4f,
                    omatsCover,
                    hemming,
                    Set.of(classics, adv),
                    new HashSet<>(),
                    new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/book/sample1.epub")).readAllBytes()));

            Book dune = new Book("Dune",
                    LocalDate.of(1965, 6, 1),
                    "Set on the desert planet Arrakis, Dune is the story of the boy Paul Atreides, heir to a noble family tasked with ruling an inhospitable world " +
                            "where the only thing of value is the “spice” melange, a drug capable of extending life and enhancing consciousness.",
                    658,
                    0.10f,
                    "0441172717",
                    4.2f,
                    duneCover,
                    herbert,
                    Set.of(fantasy, sciFi, classics, adv),
                    new HashSet<>(),
                    new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/book/sample1.epub")).readAllBytes()));

            Book dune2 = new Book("Dune Messiah",
                    LocalDate.of(1969, 7, 1),
                    "The continued story of Paul Atreides, better known--and feared--as the man christened Muad'Dib.",
                    337,
                    0.1f,
                    "978-1-69031-985-6",
                    3.8f,
                    messiahCover,
                    herbert,
                    Set.of(fantasy, sciFi, classics),
                    new HashSet<>(),
                    new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/book/sample1.epub")).readAllBytes()));

            Book dune3 = new Book("Children of Dune",
                    LocalDate.of(1976, 4, 21),
                    "The Children of Dune are twin siblings Leto and Ghanima Atreides, whose father, the Emperor Paul Muad'Dib, " +
                            "disappeared in the desert wastelands of Arrakis nine years ago.",
                    609,
                    0.1f,
                    "0450053075",
                    3.9f,
                    codCover,
                    herbert,
                    Set.of(fantasy, sciFi, classics),
                    new HashSet<>(),
                    new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/book/sample1.epub")).readAllBytes()));

            Book dorian = new Book("The Picture of Dorian Gray",
                    LocalDate.of(1980, 7, 1),
                    "The dreamlike story of a young man who sells his soul for eternal youth and beauty.",
                    272,
                    0.05f,
                    "0141439572",
                    4.1f,
                    dorianCover,
                    wilde,
                    Set.of(fantasy, classics, horror),
                    new HashSet<>(),
                    new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/book/sample1.epub")).readAllBytes()));

            Book animal = new Book("Animal Farm",
                    LocalDate.of(1945, 8, 17),
                    "A farm is taken over by its overworked, mistreated animals. With flaming idealism and stirring slogans, " +
                            "they set out to create a paradise of progress, justice, and equality.",
                    141,
                    0.05f,
                    "0452284244",
                    3.9f,
                    anFarmCover,
                    orwell,
                    Set.of(fantasy, classics, pol),
                    new HashSet<>(),
                    new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/book/sample1.epub")).readAllBytes()));

            Book nineteen = new Book("1984",
                    LocalDate.of(1949, 7, 8),
                    "Political satirist George Orwell's nightmarish vision of a totalitarian, bureaucratic world and one poor stiff's attempt to find individuality.",
                    298,
                    0.05f,
                    "1443434973",
                    4.9f,
                    cover1984,
                    orwell,
                    Set.of(classics, sciFi, pol),
                    new HashSet<>(),
                    new SerialBlob(Objects.requireNonNull(getClass().getResourceAsStream("/static/book/sample1.epub")).readAllBytes()));
            bookService.createAll(new Book[]{it, harryPotter1, harryPotter2, northernLights, lotr1,
                    lotr2, got, gatsby, meta, oldMan, dune, dune2, dune3, dorian, animal, nineteen});

            Customer user = new Customer("user", encoder.encode("user"), "user@user123.com", false, new HashSet<>());
            Customer admin = new Customer("admin", encoder.encode("admin"), "admin@admin123.com", true, new HashSet<>());
            customerService.createAll(new Customer[]{user, admin});

            Subscription sub1 = new Subscription(LocalDate.now(), null, user, lotr1);
            Subscription sub2 = new Subscription(LocalDate.now(), null, user, got);
            Subscription sub3 = new Subscription(LocalDate.now(), null, admin, dorian);
            Subscription sub4 = new Subscription(LocalDate.now(), null, admin, oldMan);
            Subscription sub5 = new Subscription(LocalDate.now(), null, admin, nineteen);
            subService.createAll(new Subscription[]{sub1, sub2, sub3, sub4, sub5});

            logger.info("Generated demo data");
        };
    }
}