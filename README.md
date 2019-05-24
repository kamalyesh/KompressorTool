# KompressorTool
  Compress all words of a language for faster retrieval

  It also provides a way to save a tree-like data structure as files. Which is, at the moment,
only applicable for the data structure, which is defined in this project.


# What does it do?
  This tool takes a word in English language, puts it into the structure (named KompressorGrid)
where each baser level item of structure (named KompressorCell) will be identiifed using the
first letter of the word, letter at the position -say i- and the letter at the position i+1.

  After putting some words into the structure, if we want to check a string is valid word or not,
we can do it using this structure.
  
  Only problem <strong>was</strong> we would have to prepare this structure over every time we
use it, which defeats the purpose of this program.

  The solution I've come up with is storing this structure in another structure, as files. The
contents of this files are numbers, numbers, numbers which will mean nothing, if you do not know
how to read them. More confusing for us Humans is that there is not a single file, but lot of
them which stores this data.

  To read this data, this code provides another function too.
  
  Another benefit of this storage structure is, <em>it reduces space required to store those words!!</em>
