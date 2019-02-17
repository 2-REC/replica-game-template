(TODO: complete and check...)

This is a template for an implementation of a game using the modified Replica Island code, ["replica-engine"](https://github.com/2-REC/replica-engine).
It serves as an example on how to use the different features of the engine. It can be used and modified as desired to adapt to a specific game.
... (TODO: continue and detail...)

----

To get the project locally and in Android Studio:

(If no Version Control system has been used before in Android Studio:
- VCS > Enable Version Control Integration
  => Select Git.
    (Enter credentials and login if required)
)

- Create a new repository to detach the project from the original repository:
  - Create a new empty repository (e.g. at GitHub)
  - "Bare-clone" this repository:
    git clone --bare URL_TO_THIS_REPOSITORY
  - Go in the newly created directory containing the cloned repository
  - Push the project to the new repository:
    git push --mirror URL_TO_NEW_REPOSITORY
  - Delete the local directory

- Start Android Studio with no opened project (close opened ones if any)

- Select "Check out project from Version Control" > "Git"
  => Specify the URL of the new repository (URL_TO_NEW_REPOSITORY) and the local destination directory, then click on "Clone".
    This step can be done outside of Android Studio, using the Git CLI (which actually makes more sense, as this repository isn't exactly an Android Studio project).

- A pop-up appears with the following message:
  "Would you like to create an Android Studio project for the sources you have checked out to ...?"
  => Select "No".

- Open an existing Android Studio project
  => Select the newly cloned repository's sub-directory "game".
    The Android Studio icon should be displayed next to the project's directory, showing that it is recognised as an Android Studio project.

- A pop-up appears with the following message:
  "Unregistered VCS root detected
  => Click on the message and select "Add root".

- Android Studio is trying to build the project (if auto build is on), which will fail (a list of dependency errors will appear).
  => This is normal, as the dependency library project isn't present yet.

- In file explorer, go to the project's directory, and find the engine's subdirectory (named "engine_git").
  => Delete its content (some content will have been generated while trying to build the project).
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

- Synchronize the projects.
  => "Sync Project with Gradle Files"


If changes have been made to the engine:
- Update the engine project
  => Pull library project.
    cd <engine_repository_directory>
    git pull
    (git pull origin master)

  If the engine is detached from the original repository (message: "HEAD detached at ..."), execute the following command to reattach:
  git chekcout master
  (or to another branch if desired)

- Commit changes
  => Project pointing to new revision of library.

