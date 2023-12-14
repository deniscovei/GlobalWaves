# GlobalWaves
## Denis Covei - 322CA

I used two design patterns in this project: **Command** and **Singleton**.

* <span style="color:green">*Command*</span> - command interface for the commands, containing the 
  action 
method
* <span style="color:#ADDFFF">*Database*</span> - singleton class that keeps track of the users,
podcasts, playlists and songs; the library is loaded here at runtime, and then for each test, the
database is reset to its initial state
* <span style="color:#ADDFFF">*AudioFileListSerializer*</span> - JSON serializer for songs

I have an abstract class <span style="color:#ADDFFF">*User*</span> that is extended by artists, 
hosts and listeners.

Each listener has a <span style="color:#ADDFFF">*Player*</span> field, in which the loaded track is
stored.
* <span style="color:#ADDFFF">*Player*</span> - class for the player, storing the current loaded
file, keeping track of the history of loaded files
* <span style="color:#ADDFFF">*Playable*</span> - class for the loaded files, with methods for
handling the state in which the current playing file is (a song in case of playlist, an episode in
case of podcast and the loaded song in case of a song playing)

Artists can add merchandise and create new events. Also, they release albums, which can be listened
by listeners.

Hosts can create new podcasts and add new episodes to them. They can also add announcements.

Each user interacts with the application through an **interface**. A page is displayed for each
user, with the available options for that user.

We have two types of files: <span style="color:#ADDFFF">*AudioCollection*</span> and
<span style="color:#ADDFFF">*AudioFile*</span>. The
<span style="color:#ADDFFF">*AudioCollection*</span> is an abstract class, extended by
<span style="color:#ADDFFF">*Playlist*</span>, <span style="color:#ADDFFF">*Album*</span> and
<span style="color:#ADDFFF">*Podcast*</span>. The <span style="color:#ADDFFF">*AudioFile*</span> is
extended by <span style="color:#ADDFFF">*Song*</span> and
<span style="color:#ADDFFF">*Episode*</span>.

**Playlists** are created by listeners, and can contain songs from different artists. They can be
public or private. They can be listened by listeners, and they can be added to the library of a
listener. They can be shuffled and repeated.

**Albums** are created by artists, and can contain songs from the same artist. They can be listened
by listeners, and they can be added to the library of a listener. They can be shuffled and
repeated.

**Podcasts** are created by hosts, and can contain episodes from the same host. They can be
listened by listeners, and they can be added to the library of a listener. They can be shuffled and
repeated.

**Songs** are created by artists, and can be listened by listeners. They can be added to the
library of a listener. They can be shuffled and repeated.

**Episodes** exist in the context of a podcast, and can be listened by listeners. They can be
added to the library of a listener.

Encountered problems:
* I had some problems with parsing JSON files. I had to write a custom serializer for the songs,
because the default one was not working properly, as I only needed to display the name of the song
as a string, not to print all attributes of the Song class instances.
* In the User class, I have a player field, that keeps track of the history of loaded files and the
state in which they remained. For each loaded file in player, I have 4 parameters: the time at
which the file started playing, a pause timestamp, if the file was paused, an elapsed time
parameter which tracks how much time did the files before the current playing one lasted, and an
offset, that I modify whenever I repeat, skip or rewind the file (as logically, the rest of the
parameters should keep their purpose). I use these parameters to calculate the time which I am at,
using a formula based on the play/pause state and the list of audio files of the audio
collections.
* I also had some trouble with shuffling, as it was hard to find out what audio file comes next
after the current playing one, taking into account the repeat and shuffle options. I solved this
by using two lists of indexes, one for the shuffled ones and one with the indexes in increasing
order (index(i) = i), and writing a nextId method that uses one of these lists, depending on the
shuffle option, and, based on the repeat option, it either returns the next index, the current or
the first one.