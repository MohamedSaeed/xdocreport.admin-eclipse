package fr.opensagres.xdocreport.admin.eclipse.ui.viewers;

import java.util.List;

import fr.opensagres.xdocreport.template.formatter.FieldMetadata;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

public class FieldsMetadataTreeModel extends FieldMetadataTreeNodeModel {

	private final FieldsMetadata metadata;

	public FieldsMetadataTreeModel(FieldsMetadata metadata) {
		super(null, null);
		this.metadata = metadata;
		this.compute();
	}

	private void compute() {
		List<FieldMetadata> fields = metadata.getFields();
		String name = null;
		String path = null;
		String[] paths = null;
		FieldMetadataTreeNodeModel parent;
		FieldMetadataTreeNodeModel node;
		for (FieldMetadata field : fields) {
			parent = null;
			node = null;
			name = field.getFieldName();
			paths = name.split("[.]");
			for (int i = 0; i < paths.length; i++) {
				path = paths[i];
				if (i == 0) {
					parent = super.getNode(path);
					if (parent == null) {
						parent = new FieldMetadataTreeNodeModel(path, this);
						super.addNode(name, parent);
					}
				} else if (i == paths.length - 1) {
					node = new FieldMetadataTreeNodeModel(path, parent, field);
					parent.addNode(name, node);
				} else {
					node = parent.getNode(path);
					if (node == null) {
						node = new FieldMetadataTreeNodeModel(path, this);
						parent.addNode(name, node);
					}
					parent = node;
				}

			}
		}
	}

}
