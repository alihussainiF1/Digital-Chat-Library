package com.book.management.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.book.management.domain.Author;
import com.book.management.domain.Book;
import com.book.management.repository.AuthorRepository;

@Component
public class LoadDataInDB implements CommandLineRunner {

	@Autowired
	private AuthorRepository authorRepository;

	@Override
	public void run(String... args) throws Exception {

		if (authorRepository.count() > 0) {
			return;
		}

		// create 3 authors with 2 books each

		// create first author and add two books
		Author author1 = new Author("Mark", "Twain");

		Book book1 = new Book("Life on the Mississippi", "Biography", 1883);
		book1.setSummary("Life on the Mississippi is a memoir by Mark Twain detailing his days as a"
				+ " steamboat pilot on the Mississippi River before and after the American Civil War.");

		Book book2 = new Book("The Adventures of Tom Sawyer", "Biography", 1876);
		book2.setSummary("The Adventures of Tom Sawyer by Mark Twain is an 1876 novel about a young boy "
				+ "growing up along the Mississippi River. The story is set in the fictional town of"
				+ " St. Petersburg, inspired by Hannibal, Missouri, where Twain lived.");

		// save data to DB
		book1.setAvailable(true);
		book2.setAvailable(true);
		author1.addBook(book1);
		author1.addBook(book2);
		authorRepository.save(author1);

		// create second author and add two books
		Author author2 = new Author("William", "Shakespeare");

		Book book3 = new Book("Hamlet", "Drama", 1599);
		book3.setSummary(
				"The Tragedy of Hamlet, Prince of Denmark, or more simply Hamlet, is a tragedy by William Shakespeare,"
						+ " believed to have been written between 1599 and 1601. The play, set in Denmark, recounts how Prince"
						+ " Hamlet exacts revenge on his uncle Claudius, who has murdered Hamlet's father, the King, and then taken"
						+ " the throne and married Gertrude, Hamlet's mother. The play vividly charts the course of real and feigned"
						+ " madness—from overwhelming grief to seething rage—and explores themes of treachery, revenge, incest, and "
						+ "moral corruption.");

		Book book4 = new Book("King Lear", "Tragedy", 1606);
		book4.setSummary("King Lear is a tragedy by William Shakespeare, believed to have been written between"
				+ " 1603 and 1606. It is considered one of his greatest works. The play is based on the legend "
				+ "of Leir of Britain, a mythological pre-Roman Celtic king. It has been widely adapted for stage"
				+ " and screen, with the part of Lear played by many of the world's most accomplished actors.");

		// save data to DB
		author2.addBook(book3);
		author2.addBook(book4);
		authorRepository.save(author2);

		// create third author and two books
		Author author3 = new Author("Leo", "Tolstoy");
		Book book5 = new Book("War and Peace", "Novel", 1869);
		book5.setSummary(
				"Epic in scale, War and Peace delineates in graphic detail events leading up to Napoleon's invasion of Russia,"
						+ " and the impact of the Napoleonic era on Tsarist society, as seen through the eyes of five Russian"
						+ " aristocratic families.");

		Book book6 = new Book("Anna Karenina", "Novel", 1877);
		book6.setSummary(
				"Anna Karenina tells of the doomed love affair between the sensuous and rebellious Anna and the dashing"
						+ " officer, Count Vronsky. Tragedy unfolds as Anna rejects her passionless marriage and must endure the"
						+ " hypocrisies of society. Set against a vast and richly textured canvas of nineteenth-century Russia, "
						+ "the novel's seven major characters create a dynamic imbalance, playing out the contrasts of city and "
						+ "country life and all the variations on love and family happiness. While previous versions have softened"
						+ " the robust, and sometimes shocking, quality of Tolstoy's writing, Pevear and Volokhonsky have produced "
						+ "a translation true to his powerful voice. This award-winning team's authoritative edition also includes "
						+ "an illuminating introduction and explanatory notes. Beautiful, vigorous, and eminently readable, this "
						+ "Anna Karenina will be the definitive text for generations to come.");

		// save data to DB
		author3.addBook(book5);
		author3.addBook(book6);
		authorRepository.save(author3);
	    Author author4 = new Author("George", "Orwell");
	    Book book7 = new Book("1984", "Dystopian", 1949);
	    book7.setSummary("A dystopian novel set in a totalitarian regime, depicting life under constant surveillance.");
	    Book book8 = new Book("Animal Farm", "Political Satire", 1945);
	    book8.setSummary("A satirical allegorical novella reflecting events leading up to the Russian Revolution.");
	    author4.addBook(book7);
	    author4.addBook(book8);
	    authorRepository.save(author4);

	    Author author5 = new Author("Jane", "Austen");
	    Book book9 = new Book("Pride and Prejudice", "Classic", 1813);
	    book9.setSummary("A romantic novel that charts the emotional development of protagonist Elizabeth Bennet.");
	    Book book10 = new Book("Sense and Sensibility", "Classic", 1811);
	    book10.setSummary("A story of two sisters, Elinor and Marianne, as they navigate love and life.");
	    author5.addBook(book9);
	    author5.addBook(book10);
	    authorRepository.save(author5);

	    Author author6 = new Author("Fyodor", "Dostoevsky");
	    Book book11 = new Book("Crime and Punishment", "Philosophical", 1866);
	    book11.setSummary("Explores the psychological anguish of a young student who plans and perpetrates a murder.");
	    Book book12 = new Book("The Brothers Karamazov", "Philosophical", 1880);
	    book12.setSummary("A passionate philosophical novel that enters deeply into ethical debates about God, free will, and morality.");
	    author6.addBook(book11);
	    author6.addBook(book12);
	    authorRepository.save(author6);

	    Author author7 = new Author("Harper", "Lee");
	    Book book13 = new Book("To Kill a Mockingbird", "Classic", 1960);
	    book13.setSummary("A novel seen through the eyes of a young girl, dealing with issues of racism and injustice.");
	    author7.addBook(book13);
	    authorRepository.save(author7);

	    Author author8 = new Author("J.R.R.", "Tolkien");
	    Book book14 = new Book("The Hobbit", "Fantasy", 1937);
	    book14.setSummary("A fantasy novel and prelude to the Lord of the Rings, following the journey of Bilbo Baggins.");
	    Book book15 = new Book("The Lord of the Rings", "Fantasy", 1954);
	    book15.setSummary("An epic high fantasy novel, following the quest to destroy the One Ring.");
	    author8.addBook(book14);
	    author8.addBook(book15);
	    book14.setAvailable(true);
	    book15.setAvailable(true);
	    authorRepository.save(author8);

	    Author author9 = new Author("Agatha", "Christie");
	    Book book16 = new Book("Murder on the Orient Express", "Mystery", 1934);
	    book16.setSummary("A detective novel featuring Hercule Poirot, solving a murder on a train.");
	    Book book17 = new Book("And Then There Were None", "Mystery", 1939);
	    book17.setSummary("A mystery novel about ten strangers who are lured to an isolated island.");
	    author9.addBook(book16);
	    author9.addBook(book17);
	    authorRepository.save(author9);
	}

}
