package abl
import java.util.UUID

import org.scalatest._
import org.scalatest.prop.PropertyChecks


class AblSpec extends WordSpec with Matchers with PropertyChecks {
  "array-based lookup" should {
    "be able to find all elements inserted" in {
      forAll("random elements") { original: Seq[(UUID, String)] =>
        val abl = ArrayBackedLookup(original)

        original.foreach { case (k, v) =>
          abl.get(k) should contain(v)
        }
      }
    }

    "not contain any keys that were not inserted" in {
      forAll("random elements", "key not in collection") {
        (original: Seq[(String, String)], unknownKey: String) =>
          val abl = ArrayBackedLookup(original)

          whenever(!original.exists(_._1 == unknownKey)) {
            abl.get(unknownKey) shouldEqual None
          }


      }
    }

  }
}
