package ch.digitalmeat.ld29;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

public class GameWorldRenderer {
	private OrthographicCamera cam = new OrthographicCamera(LD29.VIEWPORT_WIDTH, LD29.VIEWPORT_HEIGHT);
	private World world;
	private Stage stage;
	private ScalingViewport viewport;
	private Box2DDebugRenderer debugRenderer;

	public GameWorldRenderer(World world) {
		cam.zoom = 1f;
		this.world = world;
		viewport = new ScalingViewport(Scaling.fit, LD29.VIEWPORT_WIDTH, LD29.VIEWPORT_HEIGHT);
		stage = new Stage(viewport);
		debugRenderer = new Box2DDebugRenderer();
	}

	public OrthographicCamera getCam() {
		return cam;
	}

	public Stage getStage() {
		return stage;
	}

	public void draw() {
		cam.zoom = 1f;
		cam.update();
		stage.getViewport().setCamera(cam);
		stage.draw();
		cam.zoom = 1f / Entity.METERS_TO_PIXELS;
		cam.update();
		debugRenderer.render(world, cam.combined);
	}

	public void update() {
		stage.act();
	}

}
