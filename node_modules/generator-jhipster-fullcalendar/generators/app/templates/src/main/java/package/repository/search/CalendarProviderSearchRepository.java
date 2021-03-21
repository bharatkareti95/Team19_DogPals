//<--! package -->

//<--! import -->
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CalendarProvider} entity.
 */
public interface CalendarProviderSearchRepository extends ElasticsearchRepository<CalendarProvider, Long> {
}
