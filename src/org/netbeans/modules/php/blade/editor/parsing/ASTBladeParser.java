
//----------------------------------------------------
// The following code was generated by CUP v0.11a beta 20060608
// Tue Sep 06 22:04:24 EEST 2022
//----------------------------------------------------

package org.netbeans.modules.php.blade.editor.parsing;

import java.util.*;
import org.netbeans.modules.csl.api.OffsetRange;
import org.openide.util.Pair;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.*;
import org.netbeans.modules.php.editor.parser.ParserErrorHandler;
import org.netbeans.modules.php.editor.parser.astnodes.Program;

/** CUP v0.11a beta 20060608 generated parser.
  * @version Tue Sep 06 22:04:24 EEST 2022
  */
public class ASTBladeParser extends java_cup.runtime.lr_parser {

  /** Default constructor. */
  public ASTBladeParser() {super();}

  /** Constructor which sets the default scanner. */
  public ASTBladeParser(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public ASTBladeParser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\071\000\002\003\003\000\002\002\004\000\002\004" +
    "\004\000\002\004\002\000\002\010\004\000\002\010\002" +
    "\000\002\006\003\000\002\007\003\000\002\007\004\000" +
    "\002\007\010\000\002\007\006\000\002\007\010\000\002" +
    "\007\006\000\002\007\010\000\002\007\011\000\002\007" +
    "\007\000\002\007\010\000\002\007\006\000\002\007\006" +
    "\000\002\007\006\000\002\007\006\000\002\007\006\000" +
    "\002\007\007\000\002\007\010\000\002\007\006\000\002" +
    "\007\003\000\002\007\005\000\002\007\003\000\002\007" +
    "\003\000\002\007\003\000\002\007\003\000\002\007\003" +
    "\000\002\007\003\000\002\007\003\000\002\020\003\000" +
    "\002\022\010\000\002\022\006\000\002\017\004\000\002" +
    "\017\002\000\002\002\003\000\002\002\003\000\002\002" +
    "\003\000\002\002\003\000\002\002\003\000\002\013\005" +
    "\000\002\014\003\000\002\014\003\000\002\015\003\000" +
    "\002\015\003\000\002\015\003\000\002\015\005\000\002" +
    "\005\004\000\002\005\002\000\002\011\005\000\002\012" +
    "\003\000\002\012\003\000\002\012\003" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\170\000\060\002\ufffe\003\ufffe\004\ufffe\005\ufffe\006" +
    "\ufffe\007\ufffe\011\ufffe\012\ufffe\013\ufffe\014\ufffe\016\ufffe" +
    "\024\ufffe\025\ufffe\026\ufffe\027\ufffe\031\ufffe\033\ufffe\034" +
    "\ufffe\035\ufffe\040\ufffe\042\ufffe\047\ufffe\052\ufffe\001\002" +
    "\000\004\002\172\001\002\000\060\002\001\003\032\004" +
    "\013\005\015\006\036\007\012\011\024\012\034\013\023" +
    "\014\017\016\016\024\014\025\030\026\021\027\022\031" +
    "\037\033\006\034\007\035\027\040\035\042\011\047\033" +
    "\052\020\001\002\000\004\045\167\001\002\000\004\045" +
    "\162\001\002\000\104\002\ufffb\003\ufffb\004\ufffb\005\ufffb" +
    "\006\ufffb\007\ufffb\011\ufffb\012\ufffb\013\ufffb\014\ufffb\016" +
    "\ufffb\017\ufffb\020\ufffb\021\ufffb\022\ufffb\023\ufffb\024\ufffb" +
    "\025\ufffb\026\ufffb\027\ufffb\030\ufffb\031\ufffb\032\ufffb\033" +
    "\ufffb\034\ufffb\035\ufffb\036\ufffb\037\ufffb\040\ufffb\041\ufffb" +
    "\042\ufffb\047\ufffb\052\ufffb\001\002\000\106\002\uffe8\003" +
    "\uffe8\004\uffe8\005\uffe8\006\uffe8\007\uffe8\011\uffe8\012\uffe8" +
    "\013\uffe8\014\uffe8\016\uffe8\017\uffe8\020\uffe8\021\uffe8\022" +
    "\uffe8\023\uffe8\024\uffe8\025\uffe8\026\uffe8\027\uffe8\030\uffe8" +
    "\031\uffe8\032\uffe8\033\uffe8\034\uffe8\035\uffe8\036\uffe8\037" +
    "\uffe8\040\uffe8\041\uffe8\042\uffe8\047\uffe8\052\uffe8\053\157" +
    "\001\002\000\004\044\155\001\002\000\104\002\uffe6\003" +
    "\uffe6\004\uffe6\005\uffe6\006\uffe6\007\uffe6\011\uffe6\012\uffe6" +
    "\013\uffe6\014\uffe6\016\uffe6\017\uffe6\020\uffe6\021\uffe6\022" +
    "\uffe6\023\uffe6\024\uffe6\025\uffe6\026\uffe6\027\uffe6\030\uffe6" +
    "\031\uffe6\032\uffe6\033\uffe6\034\uffe6\035\uffe6\036\uffe6\037" +
    "\uffe6\040\uffe6\041\uffe6\042\uffe6\047\uffe6\052\uffe6\001\002" +
    "\000\004\053\142\001\002\000\104\002\uffe5\003\uffe5\004" +
    "\uffe5\005\uffe5\006\uffe5\007\uffe5\011\uffe5\012\uffe5\013\uffe5" +
    "\014\uffe5\016\uffe5\017\uffe5\020\uffe5\021\uffe5\022\uffe5\023" +
    "\uffe5\024\uffe5\025\uffe5\026\uffe5\027\uffe5\030\uffe5\031\uffe5" +
    "\032\uffe5\033\uffe5\034\uffe5\035\uffe5\036\uffe5\037\uffe5\040" +
    "\uffe5\041\uffe5\042\uffe5\047\uffe5\052\uffe5\001\002\000\004" +
    "\053\124\001\002\000\004\053\116\001\002\000\104\002" +
    "\uffe2\003\uffe2\004\uffe2\005\uffe2\006\uffe2\007\uffe2\011\uffe2" +
    "\012\uffe2\013\uffe2\014\uffe2\016\uffe2\017\uffe2\020\uffe2\021" +
    "\uffe2\022\uffe2\023\uffe2\024\uffe2\025\uffe2\026\uffe2\027\uffe2" +
    "\030\uffe2\031\uffe2\032\uffe2\033\uffe2\034\uffe2\035\uffe2\036" +
    "\uffe2\037\uffe2\040\uffe2\041\uffe2\042\uffe2\047\uffe2\052\uffe2" +
    "\001\002\000\004\053\111\001\002\000\004\043\106\001" +
    "\002\000\004\053\100\001\002\000\104\002\uffe4\003\uffe4" +
    "\004\uffe4\005\uffe4\006\uffe4\007\uffe4\011\uffe4\012\uffe4\013" +
    "\uffe4\014\uffe4\016\uffe4\017\uffe4\020\uffe4\021\uffe4\022\uffe4" +
    "\023\uffe4\024\uffe4\025\uffe4\026\uffe4\027\uffe4\030\uffe4\031" +
    "\uffe4\032\uffe4\033\uffe4\034\uffe4\035\uffe4\036\uffe4\037\uffe4" +
    "\040\uffe4\041\uffe4\042\uffe4\047\uffe4\052\uffe4\001\002\000" +
    "\060\002\uffff\003\uffff\004\uffff\005\uffff\006\uffff\007\uffff" +
    "\011\uffff\012\uffff\013\uffff\014\uffff\016\uffff\024\uffff\025" +
    "\uffff\026\uffff\027\uffff\031\uffff\033\uffff\034\uffff\035\uffff" +
    "\040\uffff\042\uffff\047\uffff\052\uffff\001\002\000\104\002" +
    "\ufffa\003\ufffa\004\ufffa\005\ufffa\006\ufffa\007\ufffa\011\ufffa" +
    "\012\ufffa\013\ufffa\014\ufffa\016\ufffa\017\ufffa\020\ufffa\021" +
    "\ufffa\022\ufffa\023\ufffa\024\ufffa\025\ufffa\026\ufffa\027\ufffa" +
    "\030\ufffa\031\ufffa\032\ufffa\033\ufffa\034\ufffa\035\ufffa\036" +
    "\ufffa\037\ufffa\040\ufffa\041\ufffa\042\ufffa\047\ufffa\052\ufffa" +
    "\001\002\000\004\045\075\001\002\000\004\053\065\001" +
    "\002\000\104\002\uffdf\003\uffdf\004\uffdf\005\uffdf\006\uffdf" +
    "\007\uffdf\011\uffdf\012\uffdf\013\uffdf\014\uffdf\016\uffdf\017" +
    "\uffdf\020\uffdf\021\uffdf\022\uffdf\023\uffdf\024\uffdf\025\uffdf" +
    "\026\uffdf\027\uffdf\030\uffdf\031\uffdf\032\uffdf\033\uffdf\034" +
    "\uffdf\035\uffdf\036\uffdf\037\uffdf\040\uffdf\041\uffdf\042\uffdf" +
    "\047\uffdf\052\uffdf\001\002\000\104\002\uffe0\003\uffe0\004" +
    "\uffe0\005\uffe0\006\uffe0\007\uffe0\011\uffe0\012\uffe0\013\uffe0" +
    "\014\uffe0\016\uffe0\017\uffe0\020\uffe0\021\uffe0\022\uffe0\023" +
    "\uffe0\024\uffe0\025\uffe0\026\uffe0\027\uffe0\030\uffe0\031\uffe0" +
    "\032\uffe0\033\uffe0\034\uffe0\035\uffe0\036\uffe0\037\uffe0\040" +
    "\uffe0\041\uffe0\042\uffe0\047\uffe0\052\uffe0\001\002\000\104" +
    "\002\uffe3\003\uffe3\004\uffe3\005\uffe3\006\uffe3\007\uffe3\011" +
    "\uffe3\012\uffe3\013\uffe3\014\uffe3\016\uffe3\017\uffe3\020\uffe3" +
    "\021\uffe3\022\uffe3\023\uffe3\024\uffe3\025\uffe3\026\uffe3\027" +
    "\uffe3\030\uffe3\031\uffe3\032\uffe3\033\uffe3\034\uffe3\035\uffe3" +
    "\036\uffe3\037\uffe3\040\uffe3\041\uffe3\042\uffe3\047\uffe3\052" +
    "\uffe3\001\002\000\004\053\051\001\002\000\004\053\044" +
    "\001\002\000\104\002\uffe1\003\uffe1\004\uffe1\005\uffe1\006" +
    "\uffe1\007\uffe1\011\uffe1\012\uffe1\013\uffe1\014\uffe1\016\uffe1" +
    "\017\uffe1\020\uffe1\021\uffe1\022\uffe1\023\uffe1\024\uffe1\025" +
    "\uffe1\026\uffe1\027\uffe1\030\uffe1\031\uffe1\032\uffe1\033\uffe1" +
    "\034\uffe1\035\uffe1\036\uffe1\037\uffe1\040\uffe1\041\uffe1\042" +
    "\uffe1\047\uffe1\052\uffe1\001\002\000\004\043\040\001\002" +
    "\000\060\003\ufffc\004\ufffc\005\ufffc\006\ufffc\007\ufffc\011" +
    "\ufffc\012\ufffc\013\ufffc\014\ufffc\016\ufffc\024\ufffc\025\ufffc" +
    "\026\ufffc\027\ufffc\031\ufffc\032\ufffc\033\ufffc\034\ufffc\035" +
    "\ufffc\040\ufffc\042\ufffc\047\ufffc\052\ufffc\001\002\000\060" +
    "\003\032\004\013\005\015\006\036\007\012\011\024\012" +
    "\034\013\023\014\017\016\016\024\014\025\030\026\021" +
    "\027\022\031\037\032\043\033\006\034\007\035\027\040" +
    "\035\042\011\047\033\052\020\001\002\000\102\003\ufffd" +
    "\004\ufffd\005\ufffd\006\ufffd\007\ufffd\011\ufffd\012\ufffd\013" +
    "\ufffd\014\ufffd\016\ufffd\017\ufffd\020\ufffd\021\ufffd\022\ufffd" +
    "\023\ufffd\024\ufffd\025\ufffd\026\ufffd\027\ufffd\030\ufffd\031" +
    "\ufffd\032\ufffd\033\ufffd\034\ufffd\035\ufffd\036\ufffd\037\ufffd" +
    "\040\ufffd\041\ufffd\042\ufffd\047\ufffd\052\ufffd\001\002\000" +
    "\104\002\uffef\003\uffef\004\uffef\005\uffef\006\uffef\007\uffef" +
    "\011\uffef\012\uffef\013\uffef\014\uffef\016\uffef\017\uffef\020" +
    "\uffef\021\uffef\022\uffef\023\uffef\024\uffef\025\uffef\026\uffef" +
    "\027\uffef\030\uffef\031\uffef\032\uffef\033\uffef\034\uffef\035" +
    "\uffef\036\uffef\037\uffef\040\uffef\041\uffef\042\uffef\047\uffef" +
    "\052\uffef\001\002\000\004\046\045\001\002\000\004\054" +
    "\046\001\002\000\060\003\ufffc\004\ufffc\005\ufffc\006\ufffc" +
    "\007\ufffc\011\ufffc\012\ufffc\013\ufffc\014\ufffc\016\ufffc\024" +
    "\ufffc\025\ufffc\026\ufffc\027\ufffc\031\ufffc\033\ufffc\034\ufffc" +
    "\035\ufffc\040\ufffc\041\ufffc\042\ufffc\047\ufffc\052\ufffc\001" +
    "\002\000\060\003\032\004\013\005\015\006\036\007\012" +
    "\011\024\012\034\013\023\014\017\016\016\024\014\025" +
    "\030\026\021\027\022\031\037\033\006\034\007\035\027" +
    "\040\035\041\050\042\011\047\033\052\020\001\002\000" +
    "\104\002\uffea\003\uffea\004\uffea\005\uffea\006\uffea\007\uffea" +
    "\011\uffea\012\uffea\013\uffea\014\uffea\016\uffea\017\uffea\020" +
    "\uffea\021\uffea\022\uffea\023\uffea\024\uffea\025\uffea\026\uffea" +
    "\027\uffea\030\uffea\031\uffea\032\uffea\033\uffea\034\uffea\035" +
    "\uffea\036\uffea\037\uffea\040\uffea\041\uffea\042\uffea\047\uffea" +
    "\052\uffea\001\002\000\014\046\uffcd\047\uffcd\050\uffcd\051" +
    "\uffcd\054\uffcd\001\002\000\014\046\056\047\060\050\057" +
    "\051\054\054\053\001\002\000\104\002\ufff5\003\ufff5\004" +
    "\ufff5\005\ufff5\006\ufff5\007\ufff5\011\ufff5\012\ufff5\013\ufff5" +
    "\014\ufff5\016\ufff5\017\ufff5\020\ufff5\021\ufff5\022\ufff5\023" +
    "\ufff5\024\ufff5\025\ufff5\026\ufff5\027\ufff5\030\ufff5\031\ufff5" +
    "\032\ufff5\033\ufff5\034\ufff5\035\ufff5\036\ufff5\037\ufff5\040" +
    "\ufff5\041\ufff5\042\ufff5\047\ufff5\052\ufff5\001\002\000\004" +
    "\046\063\001\002\000\016\046\uffce\047\uffce\050\uffce\051" +
    "\uffce\052\061\054\uffce\001\002\000\016\046\uffd0\047\uffd0" +
    "\050\uffd0\051\uffd0\052\uffd0\054\uffd0\001\002\000\016\046" +
    "\uffd2\047\uffd2\050\uffd2\051\uffd2\052\uffd2\054\uffd2\001\002" +
    "\000\016\046\uffd1\047\uffd1\050\uffd1\051\uffd1\052\uffd1\054" +
    "\uffd1\001\002\000\010\046\056\047\060\050\057\001\002" +
    "\000\016\046\uffcf\047\uffcf\050\uffcf\051\uffcf\052\uffcf\054" +
    "\uffcf\001\002\000\004\054\064\001\002\000\104\002\ufff4" +
    "\003\ufff4\004\ufff4\005\ufff4\006\ufff4\007\ufff4\011\ufff4\012" +
    "\ufff4\013\ufff4\014\ufff4\016\ufff4\017\ufff4\020\ufff4\021\ufff4" +
    "\022\ufff4\023\ufff4\024\ufff4\025\ufff4\026\ufff4\027\ufff4\030" +
    "\ufff4\031\ufff4\032\ufff4\033\ufff4\034\ufff4\035\ufff4\036\ufff4" +
    "\037\ufff4\040\ufff4\041\ufff4\042\ufff4\047\ufff4\052\ufff4\001" +
    "\002\000\006\046\066\047\067\001\002\000\006\051\uffd3" +
    "\054\uffd3\001\002\000\006\051\uffd4\054\uffd4\001\002\000" +
    "\006\051\072\054\071\001\002\000\104\002\ufff7\003\ufff7" +
    "\004\ufff7\005\ufff7\006\ufff7\007\ufff7\011\ufff7\012\ufff7\013" +
    "\ufff7\014\ufff7\016\ufff7\017\ufff7\020\ufff7\021\ufff7\022\ufff7" +
    "\023\ufff7\024\ufff7\025\ufff7\026\ufff7\027\ufff7\030\ufff7\031" +
    "\ufff7\032\ufff7\033\ufff7\034\ufff7\035\ufff7\036\ufff7\037\ufff7" +
    "\040\ufff7\041\ufff7\042\ufff7\047\ufff7\052\ufff7\001\002\000" +
    "\004\046\073\001\002\000\004\054\074\001\002\000\104" +
    "\002\ufff6\003\ufff6\004\ufff6\005\ufff6\006\ufff6\007\ufff6\011" +
    "\ufff6\012\ufff6\013\ufff6\014\ufff6\016\ufff6\017\ufff6\020\ufff6" +
    "\021\ufff6\022\ufff6\023\ufff6\024\ufff6\025\ufff6\026\ufff6\027" +
    "\ufff6\030\ufff6\031\ufff6\032\ufff6\033\ufff6\034\ufff6\035\ufff6" +
    "\036\ufff6\037\ufff6\040\ufff6\041\ufff6\042\ufff6\047\ufff6\052" +
    "\ufff6\001\002\000\060\003\ufffc\004\ufffc\005\ufffc\006\ufffc" +
    "\007\ufffc\011\ufffc\012\ufffc\013\ufffc\014\ufffc\016\ufffc\024" +
    "\ufffc\025\ufffc\026\ufffc\027\ufffc\031\ufffc\033\ufffc\034\ufffc" +
    "\035\ufffc\036\ufffc\040\ufffc\042\ufffc\047\ufffc\052\ufffc\001" +
    "\002\000\060\003\032\004\013\005\015\006\036\007\012" +
    "\011\024\012\034\013\023\014\017\016\016\024\014\025" +
    "\030\026\021\027\022\031\037\033\006\034\007\035\027" +
    "\036\077\040\035\042\011\047\033\052\020\001\002\000" +
    "\104\002\uffed\003\uffed\004\uffed\005\uffed\006\uffed\007\uffed" +
    "\011\uffed\012\uffed\013\uffed\014\uffed\016\uffed\017\uffed\020" +
    "\uffed\021\uffed\022\uffed\023\uffed\024\uffed\025\uffed\026\uffed" +
    "\027\uffed\030\uffed\031\uffed\032\uffed\033\uffed\034\uffed\035" +
    "\uffed\036\uffed\037\uffed\040\uffed\041\uffed\042\uffed\047\uffed" +
    "\052\uffed\001\002\000\004\015\101\001\002\000\006\051" +
    "\102\054\uffdb\001\002\000\004\015\105\001\002\000\004" +
    "\054\104\001\002\000\104\002\ufff2\003\ufff2\004\ufff2\005" +
    "\ufff2\006\ufff2\007\ufff2\011\ufff2\012\ufff2\013\ufff2\014\ufff2" +
    "\016\ufff2\017\ufff2\020\ufff2\021\ufff2\022\ufff2\023\ufff2\024" +
    "\ufff2\025\ufff2\026\ufff2\027\ufff2\030\ufff2\031\ufff2\032\ufff2" +
    "\033\ufff2\034\ufff2\035\ufff2\036\ufff2\037\ufff2\040\ufff2\041" +
    "\ufff2\042\ufff2\047\ufff2\052\ufff2\001\002\000\004\054\uffdc" +
    "\001\002\000\060\003\ufffc\004\ufffc\005\ufffc\006\ufffc\007" +
    "\ufffc\011\ufffc\012\ufffc\013\ufffc\014\ufffc\016\ufffc\024\ufffc" +
    "\025\ufffc\026\ufffc\027\ufffc\030\ufffc\031\ufffc\033\ufffc\034" +
    "\ufffc\035\ufffc\040\ufffc\042\ufffc\047\ufffc\052\ufffc\001\002" +
    "\000\060\003\032\004\013\005\015\006\036\007\012\011" +
    "\024\012\034\013\023\014\017\016\016\024\014\025\030" +
    "\026\021\027\022\030\110\031\037\033\006\034\007\035" +
    "\027\040\035\042\011\047\033\052\020\001\002\000\104" +
    "\002\ufff0\003\ufff0\004\ufff0\005\ufff0\006\ufff0\007\ufff0\011" +
    "\ufff0\012\ufff0\013\ufff0\014\ufff0\016\ufff0\017\ufff0\020\ufff0" +
    "\021\ufff0\022\ufff0\023\ufff0\024\ufff0\025\ufff0\026\ufff0\027" +
    "\ufff0\030\ufff0\031\ufff0\032\ufff0\033\ufff0\034\ufff0\035\ufff0" +
    "\036\ufff0\037\ufff0\040\ufff0\041\ufff0\042\ufff0\047\ufff0\052" +
    "\ufff0\001\002\000\012\046\uffcd\047\uffcd\050\uffcd\051\uffcd" +
    "\001\002\000\012\046\056\047\060\050\057\051\113\001" +
    "\002\000\004\046\114\001\002\000\004\054\115\001\002" +
    "\000\104\002\ufff1\003\ufff1\004\ufff1\005\ufff1\006\ufff1\007" +
    "\ufff1\011\ufff1\012\ufff1\013\ufff1\014\ufff1\016\ufff1\017\ufff1" +
    "\020\ufff1\021\ufff1\022\ufff1\023\ufff1\024\ufff1\025\ufff1\026" +
    "\ufff1\027\ufff1\030\ufff1\031\ufff1\032\ufff1\033\ufff1\034\ufff1" +
    "\035\ufff1\036\ufff1\037\ufff1\040\ufff1\041\ufff1\042\ufff1\047" +
    "\ufff1\052\ufff1\001\002\000\004\015\117\001\002\000\004" +
    "\051\120\001\002\000\004\015\121\001\002\000\006\051" +
    "\102\054\uffdb\001\002\000\004\054\123\001\002\000\104" +
    "\002\ufff3\003\ufff3\004\ufff3\005\ufff3\006\ufff3\007\ufff3\011" +
    "\ufff3\012\ufff3\013\ufff3\014\ufff3\016\ufff3\017\ufff3\020\ufff3" +
    "\021\ufff3\022\ufff3\023\ufff3\024\ufff3\025\ufff3\026\ufff3\027" +
    "\ufff3\030\ufff3\031\ufff3\032\ufff3\033\ufff3\034\ufff3\035\ufff3" +
    "\036\ufff3\037\ufff3\040\ufff3\041\ufff3\042\ufff3\047\ufff3\052" +
    "\ufff3\001\002\000\006\046\066\047\067\001\002\000\070" +
    "\003\ufffc\004\ufffc\005\ufffc\006\ufffc\007\ufffc\011\ufffc\012" +
    "\ufffc\013\ufffc\014\ufffc\016\ufffc\017\ufffc\020\ufffc\021\ufffc" +
    "\022\ufffc\023\ufffc\024\ufffc\025\ufffc\026\ufffc\027\ufffc\031" +
    "\ufffc\033\ufffc\034\ufffc\035\ufffc\040\ufffc\042\ufffc\047\ufffc" +
    "\052\ufffc\001\002\000\070\003\032\004\013\005\015\006" +
    "\036\007\012\011\024\012\034\013\023\014\017\016\016" +
    "\017\132\020\134\021\130\022\133\023\127\024\014\025" +
    "\030\026\021\027\022\031\037\033\006\034\007\035\027" +
    "\040\035\042\011\047\033\052\020\001\002\000\104\002" +
    "\uffd9\003\uffd9\004\uffd9\005\uffd9\006\uffd9\007\uffd9\011\uffd9" +
    "\012\uffd9\013\uffd9\014\uffd9\016\uffd9\017\uffd9\020\uffd9\021" +
    "\uffd9\022\uffd9\023\uffd9\024\uffd9\025\uffd9\026\uffd9\027\uffd9" +
    "\030\uffd9\031\uffd9\032\uffd9\033\uffd9\034\uffd9\035\uffd9\036" +
    "\uffd9\037\uffd9\040\uffd9\041\uffd9\042\uffd9\047\uffd9\052\uffd9" +
    "\001\002\000\104\002\uffd6\003\uffd6\004\uffd6\005\uffd6\006" +
    "\uffd6\007\uffd6\011\uffd6\012\uffd6\013\uffd6\014\uffd6\016\uffd6" +
    "\017\uffd6\020\uffd6\021\uffd6\022\uffd6\023\uffd6\024\uffd6\025" +
    "\uffd6\026\uffd6\027\uffd6\030\uffd6\031\uffd6\032\uffd6\033\uffd6" +
    "\034\uffd6\035\uffd6\036\uffd6\037\uffd6\040\uffd6\041\uffd6\042" +
    "\uffd6\047\uffd6\052\uffd6\001\002\000\104\002\uffdd\003\uffdd" +
    "\004\uffdd\005\uffdd\006\uffdd\007\uffdd\011\uffdd\012\uffdd\013" +
    "\uffdd\014\uffdd\016\uffdd\017\uffdd\020\uffdd\021\uffdd\022\uffdd" +
    "\023\uffdd\024\uffdd\025\uffdd\026\uffdd\027\uffdd\030\uffdd\031" +
    "\uffdd\032\uffdd\033\uffdd\034\uffdd\035\uffdd\036\uffdd\037\uffdd" +
    "\040\uffdd\041\uffdd\042\uffdd\047\uffdd\052\uffdd\001\002\000" +
    "\104\002\uffda\003\uffda\004\uffda\005\uffda\006\uffda\007\uffda" +
    "\011\uffda\012\uffda\013\uffda\014\uffda\016\uffda\017\uffda\020" +
    "\uffda\021\uffda\022\uffda\023\uffda\024\uffda\025\uffda\026\uffda" +
    "\027\uffda\030\uffda\031\uffda\032\uffda\033\uffda\034\uffda\035" +
    "\uffda\036\uffda\037\uffda\040\uffda\041\uffda\042\uffda\047\uffda" +
    "\052\uffda\001\002\000\104\002\uffd7\003\uffd7\004\uffd7\005" +
    "\uffd7\006\uffd7\007\uffd7\011\uffd7\012\uffd7\013\uffd7\014\uffd7" +
    "\016\uffd7\017\uffd7\020\uffd7\021\uffd7\022\uffd7\023\uffd7\024" +
    "\uffd7\025\uffd7\026\uffd7\027\uffd7\030\uffd7\031\uffd7\032\uffd7" +
    "\033\uffd7\034\uffd7\035\uffd7\036\uffd7\037\uffd7\040\uffd7\041" +
    "\uffd7\042\uffd7\047\uffd7\052\uffd7\001\002\000\104\002\uffd8" +
    "\003\uffd8\004\uffd8\005\uffd8\006\uffd8\007\uffd8\011\uffd8\012" +
    "\uffd8\013\uffd8\014\uffd8\016\uffd8\017\uffd8\020\uffd8\021\uffd8" +
    "\022\uffd8\023\uffd8\024\uffd8\025\uffd8\026\uffd8\027\uffd8\030" +
    "\uffd8\031\uffd8\032\uffd8\033\uffd8\034\uffd8\035\uffd8\036\uffd8" +
    "\037\uffd8\040\uffd8\041\uffd8\042\uffd8\047\uffd8\052\uffd8\001" +
    "\002\000\006\051\137\054\136\001\002\000\070\003\uffd5" +
    "\004\uffd5\005\uffd5\006\uffd5\007\uffd5\011\uffd5\012\uffd5\013" +
    "\uffd5\014\uffd5\016\uffd5\017\uffd5\020\uffd5\021\uffd5\022\uffd5" +
    "\023\uffd5\024\uffd5\025\uffd5\026\uffd5\027\uffd5\031\uffd5\033" +
    "\uffd5\034\uffd5\035\uffd5\040\uffd5\042\uffd5\047\uffd5\052\uffd5" +
    "\001\002\000\004\046\140\001\002\000\004\054\141\001" +
    "\002\000\104\002\uffde\003\uffde\004\uffde\005\uffde\006\uffde" +
    "\007\uffde\011\uffde\012\uffde\013\uffde\014\uffde\016\uffde\017" +
    "\uffde\020\uffde\021\uffde\022\uffde\023\uffde\024\uffde\025\uffde" +
    "\026\uffde\027\uffde\030\uffde\031\uffde\032\uffde\033\uffde\034" +
    "\uffde\035\uffde\036\uffde\037\uffde\040\uffde\041\uffde\042\uffde" +
    "\047\uffde\052\uffde\001\002\000\012\003\146\046\144\047" +
    "\147\050\057\001\002\000\104\002\ufff9\003\ufff9\004\ufff9" +
    "\005\ufff9\006\ufff9\007\ufff9\011\ufff9\012\ufff9\013\ufff9\014" +
    "\ufff9\016\ufff9\017\ufff9\020\ufff9\021\ufff9\022\ufff9\023\ufff9" +
    "\024\ufff9\025\ufff9\026\ufff9\027\ufff9\030\ufff9\031\ufff9\032" +
    "\ufff9\033\ufff9\034\ufff9\035\ufff9\036\ufff9\037\ufff9\040\ufff9" +
    "\041\ufff9\042\ufff9\047\ufff9\052\ufff9\001\002\000\010\051" +
    "\uffd0\052\uffd0\054\uffca\001\002\000\006\051\152\052\061" +
    "\001\002\000\004\054\uffc9\001\002\000\010\051\uffd1\052" +
    "\uffd1\054\uffcb\001\002\000\004\054\151\001\002\000\104" +
    "\002\uffcc\003\uffcc\004\uffcc\005\uffcc\006\uffcc\007\uffcc\011" +
    "\uffcc\012\uffcc\013\uffcc\014\uffcc\016\uffcc\017\uffcc\020\uffcc" +
    "\021\uffcc\022\uffcc\023\uffcc\024\uffcc\025\uffcc\026\uffcc\027" +
    "\uffcc\030\uffcc\031\uffcc\032\uffcc\033\uffcc\034\uffcc\035\uffcc" +
    "\036\uffcc\037\uffcc\040\uffcc\041\uffcc\042\uffcc\047\uffcc\052" +
    "\uffcc\001\002\000\004\046\153\001\002\000\004\054\154" +
    "\001\002\000\104\002\ufff8\003\ufff8\004\ufff8\005\ufff8\006" +
    "\ufff8\007\ufff8\011\ufff8\012\ufff8\013\ufff8\014\ufff8\016\ufff8" +
    "\017\ufff8\020\ufff8\021\ufff8\022\ufff8\023\ufff8\024\ufff8\025" +
    "\ufff8\026\ufff8\027\ufff8\030\ufff8\031\ufff8\032\ufff8\033\ufff8" +
    "\034\ufff8\035\ufff8\036\ufff8\037\ufff8\040\ufff8\041\ufff8\042" +
    "\ufff8\047\ufff8\052\ufff8\001\002\000\004\010\156\001\002" +
    "\000\104\002\uffe7\003\uffe7\004\uffe7\005\uffe7\006\uffe7\007" +
    "\uffe7\011\uffe7\012\uffe7\013\uffe7\014\uffe7\016\uffe7\017\uffe7" +
    "\020\uffe7\021\uffe7\022\uffe7\023\uffe7\024\uffe7\025\uffe7\026" +
    "\uffe7\027\uffe7\030\uffe7\031\uffe7\032\uffe7\033\uffe7\034\uffe7" +
    "\035\uffe7\036\uffe7\037\uffe7\040\uffe7\041\uffe7\042\uffe7\047" +
    "\uffe7\052\uffe7\001\002\000\004\046\160\001\002\000\004" +
    "\054\161\001\002\000\104\002\uffe9\003\uffe9\004\uffe9\005" +
    "\uffe9\006\uffe9\007\uffe9\011\uffe9\012\uffe9\013\uffe9\014\uffe9" +
    "\016\uffe9\017\uffe9\020\uffe9\021\uffe9\022\uffe9\023\uffe9\024" +
    "\uffe9\025\uffe9\026\uffe9\027\uffe9\030\uffe9\031\uffe9\032\uffe9" +
    "\033\uffe9\034\uffe9\035\uffe9\036\uffe9\037\uffe9\040\uffe9\041" +
    "\uffe9\042\uffe9\047\uffe9\052\uffe9\001\002\000\062\003\ufffc" +
    "\004\ufffc\005\ufffc\006\ufffc\007\ufffc\011\ufffc\012\ufffc\013" +
    "\ufffc\014\ufffc\016\ufffc\024\ufffc\025\ufffc\026\ufffc\027\ufffc" +
    "\031\ufffc\033\ufffc\034\ufffc\035\ufffc\036\ufffc\037\ufffc\040" +
    "\ufffc\042\ufffc\047\ufffc\052\ufffc\001\002\000\062\003\032" +
    "\004\013\005\015\006\036\007\012\011\024\012\034\013" +
    "\023\014\017\016\016\024\014\025\030\026\021\027\022" +
    "\031\037\033\006\034\007\035\027\036\164\037\165\040" +
    "\035\042\011\047\033\052\020\001\002\000\004\037\166" +
    "\001\002\000\104\002\uffec\003\uffec\004\uffec\005\uffec\006" +
    "\uffec\007\uffec\011\uffec\012\uffec\013\uffec\014\uffec\016\uffec" +
    "\017\uffec\020\uffec\021\uffec\022\uffec\023\uffec\024\uffec\025" +
    "\uffec\026\uffec\027\uffec\030\uffec\031\uffec\032\uffec\033\uffec" +
    "\034\uffec\035\uffec\036\uffec\037\uffec\040\uffec\041\uffec\042" +
    "\uffec\047\uffec\052\uffec\001\002\000\104\002\uffeb\003\uffeb" +
    "\004\uffeb\005\uffeb\006\uffeb\007\uffeb\011\uffeb\012\uffeb\013" +
    "\uffeb\014\uffeb\016\uffeb\017\uffeb\020\uffeb\021\uffeb\022\uffeb" +
    "\023\uffeb\024\uffeb\025\uffeb\026\uffeb\027\uffeb\030\uffeb\031" +
    "\uffeb\032\uffeb\033\uffeb\034\uffeb\035\uffeb\036\uffeb\037\uffeb" +
    "\040\uffeb\041\uffeb\042\uffeb\047\uffeb\052\uffeb\001\002\000" +
    "\060\003\ufffc\004\ufffc\005\ufffc\006\ufffc\007\ufffc\011\ufffc" +
    "\012\ufffc\013\ufffc\014\ufffc\016\ufffc\024\ufffc\025\ufffc\026" +
    "\ufffc\027\ufffc\031\ufffc\033\ufffc\034\ufffc\035\ufffc\037\ufffc" +
    "\040\ufffc\042\ufffc\047\ufffc\052\ufffc\001\002\000\060\003" +
    "\032\004\013\005\015\006\036\007\012\011\024\012\034" +
    "\013\023\014\017\016\016\024\014\025\030\026\021\027" +
    "\022\031\037\033\006\034\007\035\027\037\171\040\035" +
    "\042\011\047\033\052\020\001\002\000\104\002\uffee\003" +
    "\uffee\004\uffee\005\uffee\006\uffee\007\uffee\011\uffee\012\uffee" +
    "\013\uffee\014\uffee\016\uffee\017\uffee\020\uffee\021\uffee\022" +
    "\uffee\023\uffee\024\uffee\025\uffee\026\uffee\027\uffee\030\uffee" +
    "\031\uffee\032\uffee\033\uffee\034\uffee\035\uffee\036\uffee\037" +
    "\uffee\040\uffee\041\uffee\042\uffee\047\uffee\052\uffee\001\002" +
    "\000\004\002\000\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\170\000\006\003\003\004\004\001\001\000\002\001" +
    "\001\000\012\006\024\007\007\020\025\022\030\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\004" +
    "\011\142\001\001\000\002\001\001\000\004\013\124\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\004\010\040\001\001\000\012\006" +
    "\041\007\007\020\025\022\030\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\004\010\046\001\001\000\012\006\041\007\007\020\025" +
    "\022\030\001\001\000\002\001\001\000\004\005\051\001" +
    "\001\000\004\015\054\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\004\015\061\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\004\014" +
    "\067\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\004\010\075\001\001\000\012" +
    "\006\041\007\007\020\025\022\030\001\001\000\002\001" +
    "\001\000\002\001\001\000\004\017\102\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\004\010\106\001\001\000\012\006\041\007\007" +
    "\020\025\022\030\001\001\000\002\001\001\000\004\005" +
    "\111\001\001\000\004\015\054\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\004\017\121\001\001" +
    "\000\002\001\001\000\002\001\001\000\004\014\134\001" +
    "\001\000\004\010\125\001\001\000\014\002\130\006\041" +
    "\007\007\020\025\022\030\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\006\012\147\015\144\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\004\010\162\001\001\000\012\006\041" +
    "\007\007\020\025\022\030\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\004\010\167\001\001" +
    "\000\012\006\041\007\007\020\025\022\030\001\001\000" +
    "\002\001\001\000\002\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$ASTBladeParser$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$ASTBladeParser$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$ASTBladeParser$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 1;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}




    private ErrorStrategy defaultStrategy = new DefaultErrorStrategy();;
    private ErrorStrategy errorStrategy = defaultStrategy;

    private String fileName = null;
    private ParserErrorHandler errorHandler = null;

    public void setErrorHandler (ParserErrorHandler handler) {
        this.errorHandler = handler;
    }

    public ParserErrorHandler getErrorHandler () {
        return this.errorHandler;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        assert fileName != null;
        this.fileName = fileName;
    }


    interface ErrorStrategy {
        public boolean errorRecovery(boolean debug) throws Exception;
    }

    class DefaultErrorStrategy implements ErrorStrategy {

        public boolean errorRecovery(boolean debug) throws Exception {
            return ASTBladeParser.super.error_recovery(debug);
        }
    }

    /**
     * Attempt to recover from a syntax error.  This returns false if recovery fails,
     * true if it succeeds.
     * @param debug should we produce debugging messages as we parse.
     */
    protected boolean error_recovery(boolean debug) throws java.lang.Exception {
        return errorStrategy.errorRecovery(debug);
    }

    /**
     * Report a non fatal error (or warning).  This method takes a message
     * string and an additional object (to be used by specializations implemented in subclasses).
     * The super class prints the message to System.err.
     * @param message an error message.
     * @param info    an extra object reserved for use by specialized subclasses.
     */
    public void report_error(String message, Object info) {
        System.out.print("report_eror"  + message);
    }

    /**
     * This method is called when a syntax error has been detected and recovery is about to be invoked.
     * The super class just emit a "Syntax error" error message.
     * @param cur_token the current lookahead Symbol.
     */
    public void syntax_error(java_cup.runtime.Symbol cur_token) {
        java_cup.runtime.Symbol symbol = (java_cup.runtime.Symbol)stack.peek();
        int state = symbol.parse_state;
        short[] rowOfProbe = action_tab[state];
        if (errorHandler != null) {
            errorHandler.handleError(ParserErrorHandler.Type.SYNTAX_ERROR, rowOfProbe, cur_token, symbol);
        }
     }

    /**
     * Report a fatal error.  This method takes a message string and an additional object
     * (to be used by specializations implemented in subclasses).
     * The super class reports the error then throws an exception.
     * @param message an error message.
     * @param info    an extra object reserved for use by specialized subclasses.
     */
    public void report_fatal_error(String message, Object info) throws Exception {
        if (errorHandler != null) {
            errorHandler.handleError(ParserErrorHandler.Type.FATAL_PARSER_ERROR, null, cur_token, null);
        }
    }

    protected int error_sync_size() {
        return 1;
    }

}


