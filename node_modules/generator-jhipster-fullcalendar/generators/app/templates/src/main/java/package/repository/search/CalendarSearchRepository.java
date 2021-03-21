//<--! package -->

//<--! import -->
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Calendar} entity.
 */
public interface CalendarSearchRepository extends ElasticsearchRepository<Calendar, Long> {
}
