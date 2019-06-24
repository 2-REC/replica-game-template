# REPLICA GAME TEMPLATE

(TODO: complete and check...)<br>

This is a template for an implementation of a game using the modified Replica Island code, ["replica-engine"](https://github.com/2-REC/replica-engine).<br>

It serves as an example on how to use the different features of the engine. It can be used and modified as desired to adapt to a specific game.<br>

... (TODO: continue and detail...)<br>


## SETUP WITH GIT

This section describes the steps required to get the project up and running locally in Android Studio, keeping the dependency with the Engine repository.<br>
For a "detached" standalone setup, please look at the section "STANDALONE SETUP" below.<br>


(If no Version Control system has been used before in Android Studio:<br>
- VCS > Enable Version Control Integration<br>
  => Select Git.<br>
    (Enter credentials and login if required)<br>
)<br>

- Create a new repository to detach the project from the original repository:<br>
  - Create a new empty repository (e.g. at GitHub)<br>
  - "Bare-clone" this repository:<br>
    ```
    git clone --bare URL_TO_THIS_REPOSITORY
    ```
  - Go in the newly created directory containing the cloned repository<br>
  - Push the project to the new repository:<br>
    ```
    git push --mirror URL_TO_NEW_REPOSITORY
    ```
  - Delete the local directory<br>

- Start Android Studio with no opened project (close opened ones if any)<br>

- Select "Check out project from Version Control" > "Git"<br>
  => Specify the URL of the new repository (URL_TO_NEW_REPOSITORY) and the local destination directory, then click on "Clone".<br>
    This step can be done outside of Android Studio, using the Git CLI (which actually makes more sense, as this repository isn't exactly an Android Studio project).<br>

- A pop-up appears with the following message:<br>
  "Would you like to create an Android Studio project for the sources you have checked out to ...?"<br>
  => Select "No".<br>

- Open an existing Android Studio project<br>
  => Select the newly cloned repository's sub-directory "game".<br>
    The Android Studio icon should be displayed next to the project's directory, showing that it is recognised as an Android Studio project.<br>

- A pop-up appears with the following message:<br>
  "Unregistered VCS root detected<br>
  => Click on the message and select "Add root".<br>

- Android Studio is trying to build the project (if auto build is on), which will fail (a list of dependency errors will appear).<br>
  => This is normal, as the dependency library project isn't present yet.<br>

- In file explorer, go to the project's directory, and find the engine's subdirectory (named "engine_git").<br>
  => Delete its content (some content will have been generated while trying to build the project).<br>
    If not doing this, there will be a conflict when trying to update the engine's project.<br>
    (TODO: This is a horrible hack, there must be something wrong...)<br>

- In Android Studio, go to the "Terminal" window.<br>
  => Type the following commands:<br>
    ```
    git submodule init
    git submodule update
    ```
    (or the single combined command "git submodule --init")

- A pop-up appears with the following message:<br>
  "Unregistered VCS root detected"<br>
  => Click on the message and select "Add root".<br>

- Synchronize the projects.<br>
  => "Sync Project with Gradle Files"<br>


If changes have been made to the engine:<br>
- Update the engine project<br>
  => Pull library project.<br>
    ```
    cd <engine_repository_directory>
    git pull
    (git pull origin master)
    ```

  If the engine is detached from the original repository (message: "HEAD detached at ..."), execute the following command to reattach:<br>
  git chekcout master<br>
  (or to another branch if desired)<br>

- Commit changes<br>
  => Project pointing to new revision of library.<br>



## STANDALONE SETUP (WITHOUT GIT)

This section describes the steps required to get the project up and running locally in Android Studio, but detaching it from the Engine repository.<br>


- Clone this repository (the "game-template") to a temporary location:<br>
    ```
    git clone https://github.com/2-REC/replica-game-template
    ```

- Copy the "game" directory from the "game-template" project to a desired location:<br>

- Clone the Engine repository to a temporary location:<br>
    ```
    git clone https://github.com/2-REC/replica-engine
    ```

- Copy the "ReplicaEngine" directory from the "Engine" project to the "engine_git" subdirectory of the copied "game" directory.<br>

- Open the project in Android Studio:<br>
  => Select the "game" directory.<br>
    The Android Studio icon should be displayed next to the project's directory, showing that it is recognised as an Android Studio project.<br>
