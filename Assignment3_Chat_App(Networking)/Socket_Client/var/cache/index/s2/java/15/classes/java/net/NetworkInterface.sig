����   4 \ Y Z name Ljava/lang/String; displayName index I addrs [Ljava/net/InetAddress; bindings [Ljava/net/InterfaceAddress; childs [Ljava/net/NetworkInterface; parent Ljava/net/NetworkInterface; virtual Z defaultInterface defaultIndex <init> ()V -(Ljava/lang/String;I[Ljava/net/InetAddress;)V org.netbeans.ParameterNames getName ()Ljava/lang/String; getInetAddresses ()Ljava/util/Enumeration; 	Signature 1()Ljava/util/Enumeration<Ljava/net/InetAddress;>; getInterfaceAddresses ()Ljava/util/List; /()Ljava/util/List<Ljava/net/InterfaceAddress;>; getSubInterfaces 6()Ljava/util/Enumeration<Ljava/net/NetworkInterface;>; 	getParent ()Ljava/net/NetworkInterface; getIndex ()I getDisplayName 	getByName /(Ljava/lang/String;)Ljava/net/NetworkInterface; 
Exceptions [ 
getByIndex (I)Ljava/net/NetworkInterface; getByInetAddress 3(Ljava/net/InetAddress;)Ljava/net/NetworkInterface; addr getNetworkInterfaces getAll ()[Ljava/net/NetworkInterface; 
getByName0 getByIndex0 getByInetAddress0 isUp ()Z 
isLoopback isPointToPoint supportsMulticast getHardwareAddress ()[B getMTU 	isVirtual isUp0 (Ljava/lang/String;I)Z ind isLoopback0 supportsMulticast0 isP2P0 getMacAddr0 ([BLjava/lang/String;I)[B inAddr getMTU0 (Ljava/lang/String;I)I equals (Ljava/lang/Object;)Z obj hashCode toString init 
getDefault <clinit> 
SourceFile NetworkInterface.java SourceID 0 CompilationID 1543357640692 java/net/NetworkInterface java/lang/Object java/net/SocketException 1     
                  	    
                             $                                            !       "  # $    % &    '    	 ( )  *     +      	 , -  *     +      	 . /  *     +     0 	 1   *     +     "
 2 3  *     +
 4 )  *     +     
 5 -  *     +     
 6 /  *     +     0  7 8  *     +  9 8  *     +  : 8  *     +  ; 8  *     +  < =  *     +  > &  *     +  ? 8  
 @ A  *     +      B
 C A  *     +      B
 D A  *     +      B
 E A  *     +      B
 F G  *     +     H  B
 I J  *     +      B  K L      M  N &    O   
 P     Q $    R     S    T U    V W    X