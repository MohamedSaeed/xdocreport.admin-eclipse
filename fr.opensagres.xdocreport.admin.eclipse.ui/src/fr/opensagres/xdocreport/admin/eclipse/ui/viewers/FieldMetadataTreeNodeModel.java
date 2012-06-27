package fr.opensagres.xdocreport.admin.eclipse.ui.viewers;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import fr.opensagres.xdocreport.template.formatter.FieldMetadata;

public class FieldMetadataTreeNodeModel {

	private final String name;
	private final FieldMetadataTreeNodeModel parent;
	private final Map<String, FieldMetadataTreeNodeModel> nodesCache;
	private final FieldMetadata fieldMetadata;

	public FieldMetadataTreeNodeModel(String name,
			FieldMetadataTreeNodeModel parent) {
		this(name, parent, null);
	}

	public FieldMetadataTreeNodeModel(String name,
			FieldMetadataTreeNodeModel parent, FieldMetadata fieldMetadata) {
		this.name = name;
		this.parent = parent;
		this.nodesCache = new LinkedHashMap<String, FieldMetadataTreeNodeModel>();
		this.fieldMetadata = fieldMetadata;
	}

	public FieldMetadataTreeNodeModel getParent() {
		return parent;
	}

	public Collection<FieldMetadataTreeNodeModel> getNodes() {
		return nodesCache.values();
	}

	public FieldMetadataTreeNodeModel getNode(String name) {
		return nodesCache.get(name);
	}

	public void addNode(String name, FieldMetadataTreeNodeModel node) {
		nodesCache.put(name, node);
	}

	public FieldMetadata getFieldMetadata() {
		return fieldMetadata;
	}

	public String getName() {
		return name;
	}
}
