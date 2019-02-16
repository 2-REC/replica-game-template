(TODO: complete and check...)

This is an implementation of a game using the modified Replica Island code, ["replica-engine"](https://github.com/2-REC/replica-engine).
It serves as an exmaple on how to use the different features of the engine. It can also be used and modified as desired to adapt to a specific game.
... (TODO: continue and detail...)

----

To get the project in Android Studio from Github:

(If no Version Control system has been used before in Android Studio:
- VCS > Enable Version Control Integration
  => Select Git.
    (Enter credentials and login if required)
)

- Check out project from Version Control > Git
  => Specify URL (of this repository) & the local destination directory, then click on "Clone".

- A pop-up appears with the following message:
  "Would you like to create an Android Studio project for the sources you have checked out to ...?"
  => Select "No".

- Open an existing Android Studio project
  => Select the destination directory specified in the previous step.
  (The automatic build will fail, it is normal as the library module isn't there yet)

- In file explorer, go to the project's directory, and find the engine's subdirectory.
  => Delete its content.
    If not doing this, there will be a conflict when trying to update the engine's project.
  (TODO: This is a horrible hack, there must be something wrong...)

- In Android Studio, go to the "Terminal" window.
  => Type the following commands:
    git submodule init
    git submodule update
    (or the single combined command "git submodule --init")

- A pop-up appears with the following message:
  "Unregistered VCS root detected"
  => Click on the message and select "Add root".
  (TODO: Is this normal?)

- Synchronize the projects.
  => "Sync Project with Gradle Files"


If changes have been made to the engine:
- Update the engine project
  => Pull library project.
    cd <engine_repository_directory>
    git pull
    (git pull origin master)

- Commit changes
  => Project pointing to new revision of library.

