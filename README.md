# GlobalWaves
## Denis Covei - 322CA

### Project Structure
* <span style="color:#FFBF00">*src/*</span>
  * <span style="color:#FFBF00">*checker/*</span> - checker files
  * <span style="color:#FFBF00">*commandmanager/*</span>
    * <span style="color:#0080f0">*IO_Entry*</span> - abstract class for input/output entries
    * <span style="color:#FFBF00">*input/*</span>
      * <span style="color:#ADDFFF">*Input*</span> - contains the input parsing methods
      * <span style="color:#FFBF00">*attributes/*</span>
        * <span style="color:#ADDFFF">*Filter*</span> 
        * <span style="color:#ADDFFF">*Stats*</span>
      * <span style="color:#FFBF00">*commandstrategies/*</span>
        * <span style="color:green">*CommandStrategy*</span> - interface for the command strategies, containing the action method
        * <span style="color:#ADDFFF">*AddRemoveInPlaylistStrategy*</span> - adds or removes a song from a playlist
        * <span style="color:#ADDFFF">*BackwardStrategy*</span>
        * <span style="color:#ADDFFF">*CreatePlaylistStrategy*</span>
        * <span style="color:#ADDFFF">*FollowStrategy*</span>
        * <span style="color:#ADDFFF">*ForwardStrategy*</span>
        * <span style="color:#ADDFFF">*GetTop5PlaylistsStrategy*</span>
        * <span style="color:#ADDFFF">*GetTop5SongsStrategy*</span>
        * <span style="color:#ADDFFF">*LikeStrategy*</span>
        * <span style="color:#ADDFFF">*LoadStrategy*</span> - unselect the selected file and loads it into the player
        * <span style="color:#ADDFFF">*NextStrategy*</span>
        * <span style="color:#ADDFFF">*PlayPauseStrategy*</span>
        * <span style="color:#ADDFFF">*PrevStrategy*</span>
        * <span style="color:#ADDFFF">*RepeatStrategy*</span>
        * <span style="color:#ADDFFF">*SearchStrategy*</span> - contains the search methods with filters for each type of searched file (song, podcast, playlist)
        * <span style="color:#ADDFFF">*SelectStrategy*</span> - deletes search bar and set a selected file for a specific user
        * <span style="color:#ADDFFF">*ShowPlaylistsStrategy*</span>
        * <span style="color:#ADDFFF">*ShowPreferredSongsStrategy*</span>
        * <span style="color:#ADDFFF">*ShuffleStrategy*</span>
        * <span style="color:#ADDFFF">*StatusStrategy*</span> - get to the current playing file (taking into account the repeat and shuffle options) and prints its metadata
        * <span style="color:#ADDFFF">*SwitchStrategy*</span>
    * <span style="color:#FFBF00">*output/*</span>
      * <span style="color:#ADDFFF">*Output*</span> - contains constructors for each type of JSON outputs
  * <span style="color:#FFBF00">*data/*</span>
    * <span style="color:#ADDFFF">*Database*</span> - singleton class that keeps track of the users, podcasts, playlists and songs; the library is loaded here at runtime, and then for each test, the database is reset to its initial state
    * <span style="color:#FFBF00">*entities/*</span>
      * <span style="color:#FFBF00">*audio/*</span>
        * <span style="color:#0080f0">*File*</span> - abstract class for files
        * <span style="color:#FFBF00">*audiocollections/*</span>
          * <span style="color:#0080f0">*AudioCollection*</span> - abstract class for audio collections, storing a list of audio files
          * <span style="color:#ADDFFF">*Playlist*</span>
          * <span style="color:#ADDFFF">*Podcast*</span>
        * <span style="color:#FFBF00">*audiofiles/*</span>
          * <span style="color:#0080f0">*AudioFile*</span> - abstract class for audio files, storing their metadata
          * <span style="color:#ADDFFF">*Episode*</span>
          * <span style="color:#ADDFFF">*Song*</span>
      * <span style="color:#FFBF00">*user/*</span>
        * <span style="color:#ADDFFF">*User*</span> - class for users, storing their metadata, having a player that stores a history of loaded files, such that when multimple files are loaded into it, it can resume any previous stored file
        * <span style="color:#FFBF00">*audioplayer/*</span>
          * <span style="color:#ADDFFF">*Player*</span> - class for the player, storing the current loaded file, keeping track of the history of loaded files
          * <span style="color:#ADDFFF">*PlayerFile*</span> - class for the loaded files, with methods for handling the state in which the current playing file is (a song in case of playlist, an episode in case of podcast and the loaded song in case of a song playing)
  * <span style="color:#FFBF00">*fileio/*</span> - contains classes used to read data from the json files
  * <span style="color:#FFBF00">*main/*</span>
      * <span style="color:#ADDFFF">*Main*</span>
      * <span style="color:#ADDFFF">*Test*</span>
  * <span style="color:#FFBF00">*utils/*</span>
    * <span style="color:#ADDFFF">*AudioFileListSerializer*</span> - JSON serializer for songs
    * <span style="color:#ADDFFF">*Constants*</span> - contains constants used in the project
* input/ - contains the tests and library in JSON format
* ref/ - contains all reference output for the tests in JSON format