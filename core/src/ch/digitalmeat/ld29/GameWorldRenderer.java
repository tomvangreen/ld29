package ch.digitalmeat.ld29;

import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
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
	private RayHandler handler;

	public boolean useDebugRenderer;
	private GroundRenderer ground;
	private float sceneZoom;

	public GameWorldRenderer(World world) {
		cam.zoom = 1f;
		this.world = world;
		viewport = new ScalingViewport(Scaling.fit, LD29.VIEWPORT_WIDTH, LD29.VIEWPORT_HEIGHT);
		stage = new Stage(viewport);
		debugRenderer = new Box2DDebugRenderer();
		handler = new RayHandler(world, LD29.VIEWPORT_WIDTH, LD29.VIEWPORT_HEIGHT);
		// handler.setAmbientLight(Colors.AMBIENT_LIGHT);
		ground = new GroundRenderer();
		setSceneZoom(0.5f);
	}

	public OrthographicCamera getCam() {
		return cam;
	}

	public Stage getStage() {
		return stage;
	}

	public RayHandler getRayHandler() {
		return handler;
	}

	public void draw(Vector2 position) {
		cam.position.set(position.x, position.y, 0);

		cam.zoom = getSceneZoom();
		cam.update();
		stage.getViewport().setCamera(cam);
		ground.draw(cam);
		stage.draw();
		cam.position.set(position.x / Entity.METERS_TO_PIXELS, position.y / Entity.METERS_TO_PIXELS, 0);
		cam.zoom = getSceneZoom() / Entity.METERS_TO_PIXELS;
		cam.update();
		handler.setCombinedMatrix(cam.combined);
		handler.updateAndRender();
		if (useDebugRenderer) {
			debugRenderer.render(world, cam.combined);
		}

	}

	public void update() {
		stage.act();
	}

	public float getSceneZoom() {
		return sceneZoom;
	}

	public void setSceneZoom(float sceneZoom) {
		this.sceneZoom = sceneZoom;
	}

}
