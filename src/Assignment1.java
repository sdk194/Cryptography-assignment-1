/*
PLAN:
    PRE:
        - Generate keys
        - Do the diffie-hellman exchange (steps to do it are in the assignment page)
        - Figure out a way to read files in java
            - Pray that the binary file read is indeed in binary lol
        - Figure out a way to do exclusive or on hexadecimals
            - If there is no elegant way, create a function to convert hexadecimals to binary
            - After finishing the entirety of encryption, convert binary back to hexadecimals- Figure out a wa
        - Figure out how to grab args from command line

    MAIN PART:
        - Create a function for Cipher Block Chaining
        - Note: The assignment says you can use the built-in aes encryption function from the javax.crypto.* library
 */


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Assignment1 {
    //Given values
    String prime = "b59dd79568817b4b9f6789822d22594f376e6a9abc0241846de426e5dd8f6eddef00b465f38f509b2b18351064704fe75f012fa346c5e2c442d7c99eac79b2bc8a202c98327b96816cb8042698ed3734643c4c05164e739cb72fba24f6156b6f47a7300ef778c378ea301e1141a6b25d48f1924268c62ee8dd3134745cdf7323";
    String generator = "44ec9d52c8f9189e49cd7c70253c2eb3154dd4f08467a64a0267c9defe4119f2e373388cfa350a4e66e432d638ccdc58eb703e31d4c84e50398f9f91677e88641a2d2f6157e2f4ec538088dcf5940b053c622e53bab0b4e84b1465f5738f549664bd7430961d3e5a2e7bceb62418db747386a58ff267a9939833beefb7a6fd68";
    String givenKey = "5af3e806e0fa466dc75de60186760516792b70fdcd72a5b6238e6f6b76ece1f1b38ba4e210f61a2b84ef1b5dc4151e799485b2171fcf318f86d42616b8fd8111d59552e4b5f228ee838d535b4b987f1eaf3e5de3ea0c403a6c38002b49eade15171cb861b367732460e3a9842b532761c16218c4fea51be8ea0248385f6bac0d";

    // Secret key, remove once diffle-hellmen agreement is finished implemented: 70390995917764513604254768192752813927828121340161866492659666896520349237363420686605146457662486750682910739161370950599554944903809582867798075682681947405884792026785104262651707960691174598872062238218916187716833008177236858888577427594708180069071351782554589687319103134362024870880193867005087176969
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println(args[0]);

    }
}