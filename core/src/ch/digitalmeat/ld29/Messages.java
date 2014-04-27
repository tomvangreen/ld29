package ch.digitalmeat.ld29;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.digitalmeat.ld29.event.Events;
import ch.digitalmeat.ld29.event.ShowMessage;
import ch.digitalmeat.ld29.event.ShowMessageHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class Messages implements UiScreen, ShowMessageHandler {
	public final static float FADE_TIME = 1f;
	private Message currentMessage;
	private Table table;
	private List<Message> messages = new ArrayList<Message>();
	private Label label;

	private Map<String, Message> messageBank = new HashMap<String, Message>();
	private Map<String, List<Message>> sequenceBank = new HashMap<String, List<Message>>();

	@Override
	public void create(Stage stage) {
		Skin skin = Assets.skin;
		table = new Table(skin);
		table.setBounds(0, 0, LD29.VIEWPORT_WIDTH, LD29.VIEWPORT_HEIGHT);
		label = new Label("", skin);
		table.align(Align.top).padTop(50);
		table.add(label);
		Events.factory.getQueue().listen(ShowMessage.class, this);
		List<Message> data = Assets.getMessages();
		for (Message message : data) {
			messageBank.put(message.key, message);
			ensureSequence(message.sequence).add(message);
		}
	}

	public List<Message> ensureSequence(String key) {
		if (!sequenceBank.containsKey(key)) {
			sequenceBank.put(key, new ArrayList<Message>());
		}
		return sequenceBank.get(key);
	}

	@Override
	public void show() {
		table.setVisible(true);
	}

	@Override
	public void hide() {
		table.setVisible(false);
	}

	@Override
	public void update(float delta) {
		if (currentMessage == null && messages.size() > 0) {
			showMessage(messages.remove(0));
		}
	}

	private void showMessage(Message message) {
		currentMessage = message;
		label.addAction(alpha(0));
		label.act(1f);
		label.setText(message.message);
		//@formatter:off
		label.addAction(sequence(
			alpha(1f, FADE_TIME)
			, delay(message.onScreenTime)
			, alpha(0f, FADE_TIME)
			, run(new Runnable(){
				@Override
				public void run() {
					currentMessage = null;
				}				
			})
		));
		//@formatter:on
	}

	@Override
	public void queueMessage(String key, boolean isSequence, boolean clearExisting) {
		if (clearExisting) {
			// TODO: initiate fade in the current node, if not already running
			messages.clear();
		}
		if (isSequence) {
			List<Message> sequence = sequenceBank.get(key);
			if (sequence != null) {
				for (Message message : sequence) {
					messages.add(message);
				}
			} else {
				Gdx.app.log("Messages", "Triggered sequence not found: " + key);
			}
		} else {
			Message message = messageBank.get(key);
			if (message != null) {
				messages.add(message);
			} else {
				Gdx.app.log("Messages", "Triggered message not found: " + key);
			}
		}
	}
}
