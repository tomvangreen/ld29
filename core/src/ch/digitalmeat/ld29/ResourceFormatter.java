package ch.digitalmeat.ld29;

public class ResourceFormatter implements ProgressFormatter {

	private StringBuilder builder = new StringBuilder();
	private String resourceName;

	public ResourceFormatter(String resourceName) {
		this.resourceName = resourceName;
	}

	@Override
	public String formatProgress(float value, float max) {
		builder.setLength(0);
		if (resourceName != null && !"".equals(resourceName)) {
			builder.append(resourceName);
			builder.append(": ");
		}
		builder.append("" + ((int) value));
		builder.append("/");
		builder.append("" + ((int) max));
		String result = builder.toString();
		builder.setLength(0);
		return result;
	}
}
