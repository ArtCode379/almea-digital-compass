package almeagroup.technology.aldigitalcompass.data.repository

import almeagroup.technology.aldigitalcompass.data.model.ServiceModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalTime

class ServiceRepository {
    private val services = listOf(
        service(
            id = 1,
            name = "Digital Strategy Audit",
            description = "A structured assessment of your technology landscape, operating model, and digital priorities.",
            price = 180.0,
            times = listOf("09:00", "13:30"),
            image = "photo-1552664730-d307ca884978",
            category = "Strategy",
            duration = 90,
            features = listOf("Technology maturity scorecard", "Executive priority workshop", "90-day transformation roadmap", "Board-ready recommendations"),
        ),
        service(
            id = 2,
            name = "Cybersecurity Risk Review",
            description = "Identify critical exposure across identities, infrastructure, applications, and governance.",
            price = 220.0,
            times = listOf("10:00", "15:00"),
            image = "photo-1563013544-824ae1b704d3",
            category = "Cybersecurity",
            duration = 75,
            features = listOf("Threat surface review", "Control gap analysis", "Risk-ranked remediation plan", "Compliance alignment"),
        ),
        service(
            id = 3,
            name = "Cloud Readiness Assessment",
            description = "Build a practical migration case covering workloads, architecture, costs, and risk.",
            price = 195.0,
            times = listOf("09:30", "14:00"),
            image = "photo-1451187580459-43490279c0fa",
            category = "Cloud",
            duration = 60,
            features = listOf("Workload discovery", "Target architecture", "Cost and value model", "Migration wave plan"),
        ),
        service(
            id = 4,
            name = "Business Process Optimization",
            description = "Redesign high-friction workflows using automation, data, and measurable service targets.",
            price = 165.0,
            times = listOf("11:00", "16:00"),
            image = "photo-1551288049-bebda4e38f71",
            category = "Operations",
            duration = 60,
            features = listOf("Current-state mapping", "Bottleneck analysis", "Automation opportunities", "Performance measures"),
        ),
        service(
            id = 5,
            name = "Data & Analytics Blueprint",
            description = "Turn scattered business data into a governed analytics platform and decision system.",
            price = 210.0,
            times = listOf("10:30", "15:30"),
            image = "photo-1551288049-bebda4e38f71",
            category = "Data",
            duration = 75,
            features = listOf("Data landscape review", "Governance design", "KPI framework", "Platform roadmap"),
        ),
        service(
            id = 6,
            name = "IT Operating Model Design",
            description = "Clarify technology roles, decision rights, delivery practices, and supplier governance.",
            price = 200.0,
            times = listOf("09:00", "14:30"),
            image = "photo-1521737711867-e3b97375f902",
            category = "Strategy",
            duration = 90,
            features = listOf("Capability assessment", "Role and accountability map", "Governance cadence", "Sourcing recommendations"),
        ),
        service(
            id = 7,
            name = "Architecture Modernization",
            description = "Define a resilient target architecture that reduces complexity and accelerates delivery.",
            price = 240.0,
            times = listOf("11:30", "16:30"),
            image = "photo-1518770660439-4636190af475",
            category = "Cloud",
            duration = 90,
            features = listOf("Application portfolio review", "Target-state principles", "Integration strategy", "Modernization sequencing"),
        ),
        service(
            id = 8,
            name = "AI Opportunity Workshop",
            description = "Prioritize responsible AI use cases grounded in business value and delivery feasibility.",
            price = 175.0,
            times = listOf("10:00", "13:00"),
            image = "photo-1677442136019-21780ecad995",
            category = "AI & Data",
            duration = 60,
            features = listOf("Use-case discovery", "Value and feasibility scoring", "Responsible AI guardrails", "Pilot definition"),
        ),
        service(
            id = 9,
            name = "Digital Product Discovery",
            description = "Shape a validated product proposition, user journey, and delivery plan before development.",
            price = 185.0,
            times = listOf("09:30", "15:00"),
            image = "photo-1553877522-43269d4ea984",
            category = "Product",
            duration = 75,
            features = listOf("Problem framing", "Journey mapping", "MVP scope", "Delivery estimate"),
        ),
        service(
            id = 10,
            name = "Technology Due Diligence",
            description = "An independent review of technology assets, team, security, and scalability for transactions.",
            price = 320.0,
            times = listOf("10:00", "14:00"),
            image = "photo-1454165804606-c3d57bc86b40",
            category = "Advisory",
            duration = 120,
            features = listOf("Platform and codebase review", "Security and resilience check", "Team capability assessment", "Investment risk report"),
        ),
    )

    fun observeAll(): Flow<List<ServiceModel>> = flowOf(services)

    fun observeById(id: Int): Flow<ServiceModel?> = flowOf(getById(id))

    fun getById(id: Int): ServiceModel? = services.firstOrNull { service -> service.id == id }

    private fun service(
        id: Int,
        name: String,
        description: String,
        price: Double,
        times: List<String>,
        image: String,
        category: String,
        duration: Int,
        features: List<String>,
    ): ServiceModel {
        return ServiceModel(
            id = id,
            name = name,
            description = description,
            price = price,
            availableTime = times.map(LocalTime::parse),
            imageUrl = "https://images.unsplash.com/$image?auto=format&fit=crop&w=1200&q=85",
            category = category,
            durationMinutes = duration,
            features = features,
        )
    }
}
