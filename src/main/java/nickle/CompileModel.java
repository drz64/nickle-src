
package nickle;

import java.util.List;
import java.util.Map;

public record CompileModel(
        long memorySize,
        int userRegisters,
        List<DataDesc> staticData,
        Map<String, Long> staticOffsets,
        Map<String, Integer> labelIds
) {}
