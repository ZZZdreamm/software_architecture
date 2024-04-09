package com.avalancherush.game.Views;

import static com.avalancherush.game.Configuration.Textures.BACKGROUND;
import static com.avalancherush.game.Configuration.Textures.HOME_BUTTON;
import static com.avalancherush.game.Configuration.Textures.WOOD_BUTTON;

import com.avalancherush.game.MyAvalancheRushGame;
import com.avalancherush.game.Singletons.GameThread;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MultiPlayerView extends ScreenAdapter {

    private GameThread gameThread;
    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;
    private Rectangle joinButton;
    private Rectangle createButton;
    private Rectangle homeButton;
    private BitmapFont fontText;
    private BitmapFont fontTitle;


    public MultiPlayerView() {
        this.gameThread = GameThread.getInstance();
        this.orthographicCamera = gameThread.getCamera();
        this.batch = new SpriteBatch();

        this.joinButton = new Rectangle((MyAvalancheRushGame.INSTANCE.getScreenWidth() - WOOD_BUTTON.getWidth()) / 2, (MyAvalancheRushGame.INSTANCE.getScreenHeight() - WOOD_BUTTON.getHeight()) / 2 + 50, WOOD_BUTTON.getWidth(), WOOD_BUTTON.getHeight());
        this.createButton = new Rectangle((MyAvalancheRushGame.INSTANCE.getScreenWidth() - WOOD_BUTTON.getWidth()) / 2, joinButton.y - WOOD_BUTTON.getHeight() - 20, WOOD_BUTTON.getWidth(), WOOD_BUTTON.getHeight());
        this.homeButton = new Rectangle(50, 50, HOME_BUTTON.getWidth(), HOME_BUTTON.getHeight());

        fontText = new BitmapFont(Gdx.files.internal("font2.fnt"));
        fontText.getData().setScale(0.9f);

        fontTitle = new BitmapFont(Gdx.files.internal("font2.fnt"));
        fontTitle.getData().setScale(1f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();

        batch.draw(BACKGROUND, 0, 0, MyAvalancheRushGame.INSTANCE.getScreenWidth(), MyAvalancheRushGame.INSTANCE.getScreenHeight());

        batch.draw(WOOD_BUTTON, joinButton.x, joinButton.y);
        batch.draw(WOOD_BUTTON, createButton.x, createButton.y);

        GlyphLayout gameLogoLayout = new GlyphLayout(fontTitle, "Multiplayer");
        float gameLogoX = (MyAvalancheRushGame.INSTANCE.getScreenWidth() - gameLogoLayout.width) / 2;
        float gameLogoY = MyAvalancheRushGame.INSTANCE.getScreenHeight() - gameLogoLayout.height - 20;
        fontTitle.draw(batch, gameLogoLayout, gameLogoX, gameLogoY);

        GlyphLayout joinLayout = new GlyphLayout(fontText, "join");
        float joinTextX = joinButton.x + (WOOD_BUTTON.getWidth() - joinLayout.width) / 2;
        float joinTextY = joinButton.y + (WOOD_BUTTON.getHeight() + joinLayout.height) / 2;
        fontText.draw(batch, "join", joinTextX, joinTextY);

        GlyphLayout createLayout = new GlyphLayout(fontText, "create");
        float createTextX = createButton.x + (WOOD_BUTTON.getWidth() - createLayout.width) / 2;
        float createTextY = createButton.y + (WOOD_BUTTON.getHeight() + createLayout.height) / 2;
        fontText.draw(batch, "create", createTextX, createTextY);

        batch.draw(HOME_BUTTON, homeButton.x, homeButton.y);

        batch.end();
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 touchPos = new Vector3(screenX, screenY, 0);
                orthographicCamera.unproject(touchPos);

                if (joinButton.contains(touchPos.x, touchPos.y)) {
                    MyAvalancheRushGame.INSTANCE.setScreen(new JoinView());
                    return true;
                } else if (createButton.contains(touchPos.x, touchPos.y)) {
                    return true;
                } else if (homeButton.contains(touchPos.x, touchPos.y)) {
                    MyAvalancheRushGame.INSTANCE.setScreen(new MenuView());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void dispose() {
        batch.dispose();
        WOOD_BUTTON.dispose();
        HOME_BUTTON.dispose();
        BACKGROUND.dispose();
        fontText.dispose();
        fontTitle.dispose();
    }
}

