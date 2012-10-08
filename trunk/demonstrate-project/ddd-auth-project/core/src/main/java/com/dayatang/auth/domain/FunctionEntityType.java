package com.dayatang.auth.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;

import com.dayatang.auth.core.AuthResourceBundleI18nService;
import com.dayatang.domain.BaseEntity;
import com.dayatang.domain.InstanceFactory;

/**
 * 功能实体类型
 * 
 * @author Justin
 * 
 */
@Entity
@Table(name = "RAMS_FUNCTION_ENTITY_TYPE")
@NamedQueries( {
		@NamedQuery(name = "FunctionEntityType.findChildren", query = "from FunctionEntityType t where t.parent.id = ? order by t.name"),
		@NamedQuery(name = "FunctionEntityType.findRoot", query = "from FunctionEntityType t where t.parent.id is null order by t.name") })
public class FunctionEntityType extends BaseEntity {

	private static final long serialVersionUID = 240630512419451465L;

	@NotNull
	@Length(min = 3)
	@Column(name = "NAME", length = 50, nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = FunctionEntityType.class)
	@JoinColumn(name = "FK_PARENT_ID")
	private FunctionEntityType parent;

	public FunctionEntityType() {
		super();
	}

	public FunctionEntityType(Long id, String name, FunctionEntityType parent) {
		super();
		setId(id);
		setName(name);
		setParent(parent);
	}

	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	public FunctionEntityType getParent() {
		return parent;
	}

	protected void setParent(FunctionEntityType parent) {
		this.parent = parent;
	}

	/**
	 * ID相同，便认为是同一对象
	 */
	public boolean equals(Object arg0) {
		if (arg0 == null || !(arg0 instanceof FunctionEntityType)) {
			return false;
		}
		if (arg0 == this) {
			return true;
		}
		if (arg0 instanceof FunctionEntityType) {
			FunctionEntityType type = (FunctionEntityType) arg0;
			return new EqualsBuilder().append(id, type.id).isEquals();
		}
		return false;
	}

	public int hashCode() {
		return new HashCodeBuilder().append(id).append(name).toHashCode();
	}

	public String toString() {
		return name;
	}

	/**
	 * lingen.liu 仓储
	 */
	private static FunctionEntityTypeRepository functionEntityTypeRepository;

	public static FunctionEntityTypeRepository getFunctionEntityTypeRepository() {
		if (functionEntityTypeRepository == null) {
			functionEntityTypeRepository = InstanceFactory
					.getInstance(FunctionEntityTypeRepository.class);
		}
		return functionEntityTypeRepository;
	}

	public static void setFunctionEntityTypeRepositoryRepository(
			FunctionEntityTypeRepository functionEntityTypeRepository) {
		FunctionEntityType.functionEntityTypeRepository = functionEntityTypeRepository;
	}

	/**/// ////////////////////领域行为 start/////////////////////////////*/

	/**
	 * 新增一个功能实体类型
	 */
	public void save() {
		if (this.isExist()) {
			String ex = AuthResourceBundleI18nService.getMessage(
					"auth.functionentitytype.entityTypeIsExist", "当前实体类型已经存在");
			throw new RuntimeException(ex);
		}
		if (getParent() == this) {
			String ex = AuthResourceBundleI18nService.getMessage(
					"auth.functionentitytype.entityTypeParentIsSame",
					"父实体类型不能为当前实体类型");
			throw new RuntimeException(ex);
		}
		getFunctionEntityTypeRepository().save(this);
	}

	/**
	 * 移除一个功能实体类型
	 */
	public void remove() {
		{
			List<FunctionEntity> list = FunctionEntity
					.getFunctionEntityRepository()
					.getFunctionEntityByType(this);
			if (list != null && !list.isEmpty()) {
				for (FunctionEntity entity : list) {
					entity.remove();
				}
			}
		}
		{
			List<FunctionEntityType> list = findChildren();
			if (list != null && !list.isEmpty()) {
				for (FunctionEntityType type : list) {
					type.remove();
				}
			}
		}
		getFunctionEntityTypeRepository().remove(this);
	}

	/**
	 * 更新一个功能实体类型
	 */
	public FunctionEntityType update() {
		FunctionEntityType type = FunctionEntityType.get(getId());
		if (type == null) {
			String ex = AuthResourceBundleI18nService.getMessage(
					"auth.functionentitytype.entityTypeIsNull", "找不到实体类型");
			throw new RuntimeException(ex);
		}
		type.setName(getName());
		return getFunctionEntityTypeRepository().save(type);
	}

	public List<FunctionEntityType> findChildren() {
		return getFunctionEntityTypeRepository().findByNamedQuery(
				"FunctionEntityType.findChildren", new Object[] { getId() });
	}

	public static List<FunctionEntityType> findRoot() {
		return getFunctionEntityTypeRepository().findByNamedQuery(
				"FunctionEntityType.findRoot", new Object[0]);
	}

	/**
	 * 以Id返回一个FunctionEntityType
	 * @param id
	 * @return
	 */
	public static FunctionEntityType get(long id) {
		FunctionEntityType ft = getFunctionEntityTypeRepository().get(id);
		if (ft == null)
			throw new RuntimeException(AuthResourceBundleI18nService
					.getMessage("auth.functionentitytype.entityTypeIsNotExist",
							"当前实体类型不存在"));
		return ft;
	}

	/**
	 * 判断当前对象是否存在
	 * @return
	 */
	public boolean isExist() {
		return getFunctionEntityTypeRepository().findbyExample(this) != null;
	}
}
