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

    @OneToOne(cascade = [CascadeType.ALL])
    lateinit var responseTemplate: Response

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
    @Lob
    var body: String? = null

    @Column(columnDefinition = "TEXT")
    lateinit var headers: String
}