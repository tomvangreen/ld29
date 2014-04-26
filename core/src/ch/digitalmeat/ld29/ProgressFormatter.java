package ch.digitalmeat.ld29;

public interface ProgressFormatter {
	public String formatProgress(float value, float max);

	public class DefaultIntProgressFormatter implements ProgressFormatter {

		private String format;

		public DefaultIntProgressFormatter() {
			this("%d/%d");
		}

		public DefaultIntProgressFormatter(String format) {
			this.format = format;
		}

		@Override
		public String formatProgress(float value, float max) {
			return String.format(format, (int) value, (int) max);
		}

	}

	public class DefaultFloatProgressFormatter implements ProgressFormatter {

		private String format;

		public DefaultFloatProgressFormatter() {
			this("%.1f/%.1f");
		}

		public DefaultFloatProgressFormatter(String format) {
			this.format = format;
		}

		@Override
		public String formatProgress(float value, float max) {
			return String.format(format, value, max);
		}

	}

}
