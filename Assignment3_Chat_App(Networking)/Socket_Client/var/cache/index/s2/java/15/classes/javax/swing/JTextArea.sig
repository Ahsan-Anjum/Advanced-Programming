����   4 a [ \ ] AccessibleJTextArea InnerClasses 	uiClassID Ljava/lang/String; ConstantValue ^ rows I columns columnWidth 	rowHeight wrap Z word <init> ()V (Ljava/lang/String;)V org.netbeans.ParameterNames text (II)V (Ljava/lang/String;II)V (Ljavax/swing/text/Document;)V doc 2(Ljavax/swing/text/Document;Ljava/lang/String;II)V getUIClassID ()Ljava/lang/String; createDefaultModel ()Ljavax/swing/text/Document; 
setTabSize (I)V size 
getTabSize ()I setLineWrap (Z)V getLineWrap ()Z setWrapStyleWord getWrapStyleWord getLineOfOffset (I)I 
Exceptions _ offset getLineCount getLineStartOffset line getLineEndOffset insert (Ljava/lang/String;I)V str pos append replaceRange start end getRows setRows getRowHeight 
getColumns 
setColumns getColumnWidth getPreferredSize ()Ljava/awt/Dimension; setFont (Ljava/awt/Font;)V f paramString  getScrollableTracksViewportWidth "getPreferredScrollableViewportSize getScrollableUnitIncrement (Ljava/awt/Rectangle;II)I visibleRect orientation 	direction writeObject (Ljava/io/ObjectOutputStream;)V ` s getAccessibleContext )()Ljavax/accessibility/AccessibleContext; 
SourceFile JTextArea.java SourceID 0 CompilationID 1543672814279 javax/swing/JTextArea javax/swing/text/JTextComponent )javax/swing/JTextArea$AccessibleJTextArea 
TextAreaUI %javax/swing/text/BadLocationException java/io/IOException !              	  
                             #                       
           
                     
               !      "  # $    % &        ' (    ) &        * (    + ,  -     .     /  0 $    1 ,  -     .     2  3 ,  -     .     2  4 5      6 7  8       6  9       6 : ;  < $    = !      
  > $    ? $    @ !        A $    B C    D E      F  G     H (    I C    J K      L M N  O P  -     Q     R  S T    U    V W    X Y    Z    
     