package pl.mockify.server.data

import java.sql.Timestamp
import javax.persistence.*

@Entity
class Hook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column
    lateinit var name: String

    @Column(columnDefinition = "TEXT")
    lateinit var responseTemplate: String

    @OneToMany(cascade = [CascadeType.ALL])
    lateinit var events: List<Event>

    @Column
    lateinit var lastModified: Timestamp
}

@Entity
class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column(columnDefinition = "TEXT")
    var body: String? = null
}

@Entity
class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column
    lateinit var timestamp : Timestamp

    @OneToOne(cascade = [CascadeType.ALL])
    lateinit var request: Request

    @OneToOne(cascade = [CascadeType.ALL])
    lateinit var response: Response
}

@Entity
class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column
    lateinit var method: String

    @Column(columnDefinition = "TEXT")
    var body: String? = null

    @Column(columnDefinition = "TEXT")
    lateinit var headers: String
}