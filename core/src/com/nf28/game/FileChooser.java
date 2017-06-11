package com.nf28.game;

/**
 * Created by Rachid Khalef on 28/05/2017.
 */


        import java.util.Arrays;
        import java.util.Comparator;

        import com.badlogic.gdx.Application;
        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.files.FileHandle;
        import com.badlogic.gdx.graphics.Color;
        import com.badlogic.gdx.scenes.scene2d.InputEvent;
        import com.badlogic.gdx.scenes.scene2d.Stage;
        import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
        import com.badlogic.gdx.scenes.scene2d.ui.Label;
        import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
        import com.badlogic.gdx.scenes.scene2d.ui.Skin;
        import com.badlogic.gdx.scenes.scene2d.ui.Table;
        import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class FileChooser extends Dialog {

    private Skin skin;
    private FileHandle directory;
    private FileHandle file;

    public FileHandle getFile() {
        return file;
    }

    @Override
    public Dialog show(Stage stage) {
        return super.show(stage);
    }

    public void setDirectory(FileHandle directory) {
        if (this.directory != directory) {
            this.directory = directory;
            this.file = null;
            buildList();
        }
    }

    public void setFile(FileHandle file) {
        if (this.file != file) {
            if (this.file != null) {
                Label label = (Label) this.findActor(this.file.name());
                label.setColor(Color.WHITE);
            }
            Label label = (Label) this.findActor(file.name());
            label.setColor(Color.RED);
            this.file = file;
        }
    }

    public FileChooser(String title, Skin skin) {
        super(title, skin);
        this.getCell(getButtonTable()).expandX().fill();
        this.getButtonTable().defaults().expandX().fill();

        this.button("Cancel", "Cancel");
        this.button("OK", "OK");

        this.setModal(true);
        this.skin = skin;
    }

    public FileChooser(String title, Skin skin, String windowStyleName) {
        super(title, skin, windowStyleName);
        this.setModal(true);
        this.skin = skin;
    }

    private void buildList() {
        FileHandle[] files = directory.list();
        Arrays.sort(files, new Comparator<FileHandle>() {
            @Override
            public int compare(FileHandle o1, FileHandle o2) {
                if (o1.isDirectory() && !o2.isDirectory()) {
                    return -1;
                }
                if (o2.isDirectory() && !o1.isDirectory()) {
                    return +1;
                }
                return o1.name().compareToIgnoreCase(o2.name());
            }
        });
        ScrollPane pane = new ScrollPane(null, skin);
        final Table table = new Table().top().left();
        table.defaults().left();
        ClickListener fileClickListener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Label target = (Label) event.getTarget();
                if (target.getName().equals("..")) {
                    setDirectory(directory.parent());
                } else {
                    FileHandle handle = directory.child(target.getName());
                    if (handle.isDirectory()) {
                        setDirectory(handle);
                    } else {
                        if (!FileLoader.checkFile(handle)){
                            ErrorDialog errorDialog = new ErrorDialog("Error", skin, new Label("File could not be read, please see if it is a Comma-separated values file", skin));
                            errorDialog.showMessage();
                            errorDialog.getBackground().setMinWidth(150);
                            errorDialog.getBackground().setMinHeight(100);
                            errorDialog.show(getStage());

                        }
                        else if(FileLoader.checkExistence(handle)){
                            ErrorDialog errorDialog = new ErrorDialog("Error", skin, new Label("File with the same name already exists, please rename it or remove before import", skin));
                            errorDialog.showMessage();
                            errorDialog.getBackground().setMinWidth(150);
                            errorDialog.getBackground().setMinHeight(100);
                            errorDialog.show(getStage());
                        }
                        else {
                            Gdx.app.log("FileChooser", " Check Success ");
                            setFile(handle);
                        }
                    }
                }
            }
        };

        table.row();
        Label current_dir = new Label(
                "Current Directory :" + this.directory.path(), skin);
        table.add(current_dir);
        table.row();
        Label label = new Label("..", skin);
        label.setName("..");
        label.addListener(fileClickListener);
        table.add(label).expandX().fillX();
        for (FileHandle file : files) {
            Gdx.app.setLogLevel(Application.LOG_DEBUG);
            if (file.exists()) {
                Gdx.app.log(" File Name : ", file.path());
            }
            else {
                Gdx.app.log(" File Name : ", " Null ");
            }
            table.row();
            label = new Label(file.name(), skin);
            label.setName(file.name());
            label.addListener(fileClickListener);
            table.add(label).expandX().fillX();
        }
        pane.setWidget(table);
        this.getContentTable().reset();
        this.getContentTable().add(pane).maxHeight(300).expand().fill();
    }

}