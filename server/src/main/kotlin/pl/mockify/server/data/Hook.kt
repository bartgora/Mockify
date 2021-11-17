package pl.mockify.server.data
import javax.persistence.*


@Entity
class Hook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column
    lateinit var name: String

    @OneToOne
    lateinit var responseTemplate: Response

    @OneToMany()
    lateinit var events: List<Event>
}

@Entity
class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column
    lateinit var body: String
}

@Entity
class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0;

    @OneToOne
    lateinit var request: Request

    @OneToOne
    lateinit var response: Response
}

@Entity
class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column
    lateinit var method: String

    @Column
    lateinit var body: String

    @Column
    lateinit var headers: String
}