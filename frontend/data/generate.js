const fs = require("node:fs");

// Helper function to generate random dates
function getRandomDate(start, end) {
  return new Date(
    start.getTime() + Math.random() * (end.getTime() - start.getTime())
  );
}

const additionalAuthorNames = [
  "J.K. Rowling",
  "Ernest Hemingway",
  "Mark Twain",
  "Mary Shelley",
  "Charles Dickens",
  "J.R.R. Tolkien",
  "Stephen King",
  "George R.R. Martin",
  "Virginia Woolf",
  "Franz Kafka",
  "Isaac Asimov",
  "Ray Bradbury",
  "Arthur Conan Doyle",
  "Agatha Christie",
  "Louisa May Alcott",
  "Gabriel Garcia Marquez",
  "James Joyce",
  "Fyodor Dostoevsky",
  "Jane Austen",
  "Oscar Wilde",
].map((s) => s.replace("'", ""));

const additionalTitles = [
  "Harry Potter and the Sorcerer's Stone",
  "The Old Man and the Sea",
  "The Adventures of Huckleberry Finn",
  "Frankenstein",
  "A Tale of Two Cities",
  "The Hobbit",
  "The Shining",
  "A Game of Thrones",
  "Mrs. Dalloway",
  "The Metamorphosis",
  "Foundation",
  "Fahrenheit 451",
  "Sherlock Holmes: The Complete Novels and Stories",
  "Murder on the Orient Express",
  "Little Women",
  "One Hundred Years of Solitude",
  "Ulysses",
  "Crime and Punishment",
  "Emma",
  "The Picture of Dorian Gray",
].map((s) => s.replace("'", ""));

const additionalSynopses = [
  "A young boy discovers he's a wizard and attends a magical school, where he makes friends and uncovers the truth about his parents' deaths.",
  "An aging fisherman struggles to catch a giant marlin, battling the sea and his own limitations.",
  "The adventures of a young boy and a runaway slave as they travel down the Mississippi River on a raft.",
  "A scientist creates a monster in his quest to uncover the secrets of life and death, with tragic consequences.",
  "The dramatic story of the French Revolution, focusing on the lives of several characters whose fates are intertwined.",
  "A hobbit is reluctantly drawn into an epic quest to reclaim a lost kingdom and defeat a dragon.",
  "A family moves into an isolated hotel for the winter, where a sinister presence influences the father into violence.",
  "Noble families vie for control of the Iron Throne, while ancient threats rise in the north.",
  "A day in the life of a high-society woman in post-World War I England, exploring themes of mental illness and feminism.",
  "A man wakes up to find himself transformed into a giant insect, struggling to adjust to his new reality.",
  "A scientist creates a vast galactic empire based on his predictions of the future, attempting to save humanity from chaos.",
  "In a future society where books are banned, a fireman begins to question his role in maintaining the status quo.",
  "The adventures of the world's greatest detective and his loyal friend, Dr. Watson, as they solve crimes in Victorian England.",
  "A detective must solve a murder mystery aboard a luxury train, uncovering a web of secrets and lies.",
  "The lives and loves of the four March sisters during the American Civil War.",
  "A multi-generational epic that chronicles the rise and fall of the Buendía family in a fictional town in Colombia.",
  "A modernist novel that follows a single day in the life of Leopold Bloom in Dublin.",
  "A young man's spiritual awakening leads him to confront the darkest aspects of human nature.",
  "A young woman navigates the complex social structure of her time, learning about love and relationships.",
  "A young man sells his soul to maintain his youth and beauty, with horrifying results.",
].map((s) => s.replace("'", ""));

const additionalCoverUrls = [
  "https://covers.openlibrary.org/b/id/8114441-L.jpg",
  "https://covers.openlibrary.org/b/id/8227781-L.jpg",
  "https://covers.openlibrary.org/b/id/8104416-L.jpg",
  "https://covers.openlibrary.org/b/id/8225553-L.jpg",
  "https://covers.openlibrary.org/b/id/7222511-L.jpg",
  "https://covers.openlibrary.org/b/id/7222271-L.jpg",
  "https://covers.openlibrary.org/b/id/8228782-L.jpg",
  "https://covers.openlibrary.org/b/id/7984912-L.jpg",
  "https://covers.openlibrary.org/b/id/7230562-L.jpg",
  "https://covers.openlibrary.org/b/id/8222322-L.jpg",
  "https://covers.openlibrary.org/b/id/8227782-L.jpg",
  "https://covers.openlibrary.org/b/id/8101427-L.jpg",
  "https://covers.openlibrary.org/b/id/8228692-L.jpg",
  "https://covers.openlibrary.org/b/id/7222247-L.jpg",
  "https://covers.openlibrary.org/b/id/8101428-L.jpg",
  "https://covers.openlibrary.org/b/id/8225554-L.jpg",
  "https://covers.openlibrary.org/b/id/7222513-L.jpg",
  "https://covers.openlibrary.org/b/id/7222274-L.jpg",
  "https://covers.openlibrary.org/b/id/8228783-L.jpg",
  "https://covers.openlibrary.org/b/id/7984914-L.jpg",
].map((s) => s.replace("'", ""));

// Combine initial data with additional data
const authorNames = [
  "Harper Lee",
  "George Orwell",
  "Jane Austen",
  "F. Scott Fitzgerald",
  "Herman Melville",
  "Leo Tolstoy",
  "J.D. Salinger",
  "J.R.R. Tolkien",
  "C.S. Lewis",
  "Charlotte Brontë",
  ...additionalAuthorNames,
].map((s) => s.replace("'", ""));

const titles = [
  "To Kill a Mockingbird",
  "1984",
  "Pride and Prejudice",
  "The Great Gatsby",
  "Moby-Dick",
  "War and Peace",
  "The Catcher in the Rye",
  "The Lord of the Rings",
  "The Chronicles of Narnia",
  "Jane Eyre",
  ...additionalTitles,
].map((s) => s.replace("'", ""));

const synopses = [
  "A novel about the serious issues of rape and racial inequality narrated by the young Scout Finch in the Deep South.",
  "A dystopian novel set in a totalitarian society ruled by Big Brother, where truth is manipulated and freedom is restricted.",
  "A classic romantic novel that explores the issues of class, marriage, and morality in 19th century England.",
  "A novel about the American Dream gone wrong, featuring the mysterious millionaire Jay Gatsby and his obsession with Daisy Buchanan.",
  "The epic tale of Captain Ahab's obsessive quest to kill the giant white whale, Moby Dick.",
  "A historical novel that chronicles the lives of Russian aristocrats during the Napoleonic era.",
  "A story about teenage angst and alienation, narrated by the disaffected Holden Caulfield.",
  "An epic fantasy saga about the quest to destroy the One Ring and the battle between good and evil in Middle-earth.",
  "A series of seven fantasy novels that take place in the magical land of Narnia.",
  "A novel about the experiences of the titular character, including her growth to adulthood and her love for Mr. Rochester.",
  ...additionalSynopses,
].map((s) => s.replace("'", ""));

const coverUrls = [
  "https://covers.openlibrary.org/b/id/8228691-L.jpg",
  "https://covers.openlibrary.org/b/id/7222246-L.jpg",
  "https://covers.openlibrary.org/b/id/8101426-L.jpg",
  "https://covers.openlibrary.org/b/id/8225551-L.jpg",
  "https://covers.openlibrary.org/b/id/7222512-L.jpg",
  "https://covers.openlibrary.org/b/id/7222273-L.jpg",
  "https://covers.openlibrary.org/b/id/8228781-L.jpg",
  "https://covers.openlibrary.org/b/id/7984916-L.jpg",
  "https://covers.openlibrary.org/b/id/7230561-L.jpg",
  "https://covers.openlibrary.org/b/id/8222321-L.jpg",
  ...additionalCoverUrls,
].map((s) => s.replace("'", ""));
// Helper function to generate random ISBN numbers
function getRandomISBN() {
  return "978" + Math.floor(Math.random() * 1000000000 + 1000000000).toString();
}

function mapData(data) {
  return data.map((d) => `(${d.join(",")})`);
}

// Generate 10 random entries
function main() {
  const data = [];
  const al = authorNames.length;
  const cl = coverUrls.length;
  const sl = synopses.length;
  const tl = titles.length;

  for (let j = 0; j < 200000; j++) {
    const created_by = Math.floor(Math.random() * 2) + 1;
    const createdDate = getRandomDate(
      new Date(2000, 0, 1),
      new Date()
    ).toISOString();
    const authorName = authorNames[Math.floor(Math.random() * al)];
    const coverUrl = coverUrls[Math.floor(Math.random() * cl)];
    const isbn = getRandomISBN();
    const synopsis = synopses[Math.floor(Math.random() * sl)];
    const title = titles[Math.floor(Math.random() * tl)];
    const owner_id = created_by;

    data.push([
      `'${j + 1}'`,
      `'${created_by}'`,
      `'${createdDate}'`,
      `NULL`,
      `NULL`,
      `false`,
      `'${authorName}'`,
      `'${coverUrl}'`,
      `'${isbn}'`,
      `true`,
      `'${synopsis}'`,
      `'${title}'`,
      `'${owner_id}'`,
    ]);
  }

  const content = `
  INSERT INTO book (id, created_by, created_date, last_modified_by, last_modified_date, archived, author_name, book_cover, isbn, shareable, synopsis, title, owner_id)
  VALUES
  ${mapData(data)};
  `;

  try {
    fs.writeFileSync("./data/books.sql", content);
    // file written successfully
  } catch (err) {
    console.error(err);
  }
}

main();
