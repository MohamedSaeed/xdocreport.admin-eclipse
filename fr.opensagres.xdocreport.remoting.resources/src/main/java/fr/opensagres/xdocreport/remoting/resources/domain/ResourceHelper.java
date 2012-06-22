package fr.opensagres.xdocreport.remoting.resources.domain;

public class ResourceHelper
{

    public static Resource createResource( String name, ResourceType type, Resource linkedResource )
    {
        Resource resource = new Resource();
        resource.setName( name );
        resource.setType( type );
        if ( linkedResource != null )
        {
            linkedResource.getChildren().add( resource );
        }
        return resource;
    }

    public static Resource createCategory( String name, Resource linkedResource )
    {
        return createResource( name, ResourceType.FOLDER, linkedResource );
    }

    public static Resource createDocument( String name, Resource linkedResource )
    {
        return createResource( name, ResourceType.FILE, linkedResource );
    }

    public static Resource createTemplate( String name, Resource linkedResource )
    {
        String templateName = name;
        int index = name.lastIndexOf( '.' );
        if ( index != -1 )
        {
            templateName = templateName.substring( 0, index );
        }
        Resource template = createResource( templateName, ResourceType.TEMPLATE, linkedResource );
        // Add document (docx, odt...)
        createDocument( name, template );
        // Add META-INF folder
        Resource metaInf = createCategory( "META-INF", template );
        // Add *fields.xml
        StringBuilder fieldsMetadata = new StringBuilder( templateName );
        fieldsMetadata.append( ".fields.xml" );
        createDocument( fieldsMetadata.toString(), metaInf );

        return template;
    }
}
